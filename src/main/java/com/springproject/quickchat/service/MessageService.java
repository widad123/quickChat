package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.FileDTO;
import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.File;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.MessageRepositoryInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepositoryInterface messageRepository;
    private final DiscussionService discussionService;

    public MessageService(MessageRepositoryInterface messageRepository, DiscussionService discussionService) {
        this.messageRepository = messageRepository;
        this.discussionService = discussionService;
    }

    public void sendMessage(Long senderId, MessageDTO messageDTO, FileDTO fileDTO) {
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

        return message;
    }

    public void removeFileFromMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + messageId));

        if (message.getAttachedFile() != null) {
            message.removeFile();
            messageRepository.save(message);
        } else {
            throw new IllegalArgumentException("No file attached to the message with id: " + messageId);
        }
    }

}
