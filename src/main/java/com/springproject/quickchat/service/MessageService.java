package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Message message) {
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
