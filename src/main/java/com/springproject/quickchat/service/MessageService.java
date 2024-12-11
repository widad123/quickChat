package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.FileDTO;
import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.File;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.MessageRepositoryInterface;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepositoryInterface messageRepository;
    private final DiscussionService discussionService;
    private final NotificationService notificationService;


    public MessageService(MessageRepositoryInterface messageRepository,
                          DiscussionService discussionService,
                          NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.discussionService = discussionService;
        this.notificationService = notificationService;
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof String) {
            try {
                return Long.valueOf(authentication.getPrincipal().toString());
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Le principal n'est pas un ID utilisateur valide : " + authentication.getPrincipal());
            }
        }
        throw new IllegalStateException("Utilisateur non authentifié ou principal invalide.");
    }


    public void sendMessage(MessageDTO messageDTO, FileDTO fileDTO) {
        Long senderId = getCurrentUserId();
        Long discussionId = discussionService.findOrCreateDiscussion(senderId, messageDTO.idRecipient());

        File attachedFile = null;
        if (fileDTO != null) {
            attachedFile = File.create(
                    null,
                    fileDTO.getFileName(),
                    fileDTO.getFileType(),
                    fileDTO.getFileSize(),
                    fileDTO.getFileUrl(),
                    false
            );
        }

        Message message = Message.create(
                null,
                discussionId,
                senderId,
                messageDTO.idRecipient(),
                messageDTO.content(),
                LocalDateTime.now(),
                attachedFile
        );

        messageRepository.save(message);

        if (attachedFile != null) {
            notificationService.sendNotificationToDiscussion(
                    discussionId,
                    "new-file-received",
                    "Un nouveau fichier a été envoyé dans la discussion."
            );
        }else {
            notificationService.sendNotification(
                    messageDTO.idRecipient(),
                    "new-message-received",
                    "Vous avez reçu un nouveau message dans une discussion."
            );
        }
    }

    public List<Message> getMessagesForDiscussion(Long discussionId) {
        return messageRepository.findByDiscussionId(discussionId).stream()
                .filter(message -> !message.isDeleted())
                .collect(Collectors.toList());
    }

    public Message editMessage(Long messageId, String newContent) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message introuvable."));

        message.editContent(newContent);
        message.setEdited(true);

        messageRepository.save(message);

        notificationService.sendNotificationToDiscussion(
                message.getDiscussionId(),
                "message-edited",
                "Le message ID " + messageId + " a été modifié."
        );

        return message;
    }

    public void removeFileFromMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + messageId));

        if (message.getAttachedFile() == null) {
            throw new IllegalStateException("No file attached to the message with id: " + messageId);
        }

        message.removeFile();
        messageRepository.save(message);

        notificationService.sendNotificationToDiscussion(
                message.getDiscussionId(),
                "file-removed",
                "Un fichier a été supprimé du message dans la discussion."
        );
    }


    public void deleteMessage(Long messageId) {
        Long userId = getCurrentUserId();
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message introuvable : " + messageId));

        if (!message.getSender().equals(userId)) {
            throw new SecurityException("Vous n'êtes pas autorisé à supprimer ce message.");
        }

        message.setDeleted(true);
        messageRepository.save(message);

        notificationService.sendNotificationToDiscussion(
                message.getDiscussionId(),
                "message-deleted",
                "Un message dans la discussion a été supprimé."
        );
    }

}
