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

    /*public List<Message> getMessagesForDiscussion(String discussionId) {
        return messages.stream()
                .filter(message -> message.getDiscussionId().equals(discussionId))
                .collect(Collectors.toList());
    }

    public Message editMessage(String messageId, String newContent) {
        for (Message message : messages) {
            if (message.getId().equals(messageId)) {
                message.setContent(newContent);
                return message;
            }
        }
        throw new IllegalArgumentException("Message ID non trouvé : " + messageId);
    }

    public void deleteMessage(String messageId) {
        for (Message message : messages) {
            if (message.getId().equals(messageId)) {
                message.markAsDeleted();
                return;
            }
        }
        throw new IllegalArgumentException("Message ID non trouvé : " + messageId);
    }*/
}
