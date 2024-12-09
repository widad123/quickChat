package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final DiscussionRepository discussionRepository;

    public MessageService(MessageRepository messageRepository, DiscussionRepository discussionRepository) {
        this.messageRepository = messageRepository;
        this.discussionRepository = discussionRepository;
    }

    public void sendMessage(String senderId, MessageDTO messageDTO) {

        String discussionId = discussionRepository.findDiscussionByUsers(senderId, messageDTO.idRecipient());

        if (discussionId == null) {
            throw new IllegalArgumentException("La discussion entre les utilisateurs n'existe pas.");
        }

        String messageId = UUID.randomUUID().toString();

        Message message = Message.create(
                messageId,
                discussionId,
                senderId,
                messageDTO.idRecipient(),
                messageDTO.content(),
                LocalDateTime.now()
        );

        messageRepository.save(message);
    }

    public List<Message> getMessagesForDiscussion(String discussionId) {
        return messageRepository.findMessagesByDiscussionId(discussionId).stream()
                .filter(message -> !message.isDeleted())
                .collect(Collectors.toList());
    }


    public Message editMessage(String messageId, String newContent) {
        Message message = messageRepository.findMessageById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message introuvable."));

        System.out.println("Editing message: " + message.getId() + ", deleted = " + message.isDeleted());

        if (message.isDeleted()) {
            System.out.println("Cannot edit a deleted message: " + messageId);
            throw new IllegalArgumentException("Impossible d'éditer un message supprimé.");
        }

        message.editContent(newContent);
        message.setEdited(true);

        messageRepository.save(message);

        return message;
    }

}