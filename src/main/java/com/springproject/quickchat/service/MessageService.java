package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private DiscussionRepository discussionRepository;
    private MessageRepository messageRepository;

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
        if (discussionRepository.findById(message.getDiscussionId()) == null) {
            throw new IllegalArgumentException("La discussion spécifiée n'existe pas.");
        }
        this.messageRepository.save(message);
    }

    public Message editMessage(String messageId, String newContent) {

        Message message = messageRepository.findById(messageId);

        if (message == null || message.isDeleted()) {
            throw new IllegalArgumentException("Message introuvable ou déjà supprimé.");
        }

        if (newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du message ne peut pas être vide.");
        }
        if (newContent.length() > 500) {
            throw new IllegalArgumentException("Le contenu du message ne doit pas dépasser 500 caractères.");
        }

        message.setContent(newContent);
        message.setEdited(true);

        return message;
    }


}
