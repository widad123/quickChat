package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        Message message = Message.create(
                null,
                discussionId,
                senderId,
                messageDTO.idRecipient(),
                messageDTO.content(),
                LocalDateTime.now()
        );

        messageRepository.save(message);
    }

    public List<Message> getMessagesForDiscussion(String discussionId) {
        return messageRepository.findMessagesByDiscussionId(discussionId);
    }

}