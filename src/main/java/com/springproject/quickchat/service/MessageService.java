package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final DiscussionRepository discussionRepository;
    private final List<Message> messages = new ArrayList<>();

    public MessageService(MessageRepository messageRepository, DiscussionRepository discussionRepository) {
        this.messageRepository = messageRepository;
        this.discussionRepository = discussionRepository;
    }

    public void sendMessage(Message message) {
        if (message.getContent().length() > 500) {
            throw new IllegalArgumentException("Le message ne doit pas dépasser 500 caractères.");
        }
        if (message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Le message ne peut pas être vide.");
        }

        Discussion discussion = discussionRepository.findById(message.getDiscussionId());
        if (discussion == null) {
            throw new IllegalArgumentException("La discussion spécifiée n'existe pas.");
        }

        messages.add(message);
        messageRepository.save(message);
    }

    public List<Message> getMessagesForDiscussion(String discussionId) {
        return messages.stream()
                .filter(message -> message.getDiscussionId().equals(discussionId))
                .toList();
    }
}
