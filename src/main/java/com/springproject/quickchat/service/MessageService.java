package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.FileDTO;
import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.File;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.model.FileLog;
import com.springproject.quickchat.model.MessageLog;
import com.springproject.quickchat.repository.FileLogRepository;
import com.springproject.quickchat.repository.MessageLogRepository;
import com.springproject.quickchat.repository.MessageRepositoryInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepositoryInterface messageRepository;
    private final DiscussionService discussionService;
    private final MessageLogRepository messageLogRepository;
    private final FileLogRepository fileLogRepository;

    public MessageService(MessageRepositoryInterface messageRepository,
                          DiscussionService discussionService,
                          MessageLogRepository messageLogRepository,
                          FileLogRepository fileLogRepository) {
        this.messageRepository = messageRepository;
        this.discussionService = discussionService;
        this.messageLogRepository = messageLogRepository;
        this.fileLogRepository = fileLogRepository;
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

        MessageLog messageLog = new MessageLog(
                senderId,
                messageDTO.idRecipient(),
                messageDTO.content(),
                LocalDateTime.now()
        );
        messageLogRepository.save(messageLog);

        if (fileDTO != null) {
            FileLog fileLog = new FileLog(
                    senderId,
                    messageDTO.idRecipient(),
                    fileDTO.getFileName(),
                    LocalDateTime.now()
            );
            fileLogRepository.save(fileLog);
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
