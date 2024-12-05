package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final List<Message> messages = new ArrayList<>();

    public Message sendMessage(String discussionId, String sender, String content) {
        Message message = new Message(
                String.valueOf(messages.size() + 1),
                discussionId,
                sender,
                content,
                LocalDateTime.now().toString()
        );
        messages.add(message);
        return message;
    }

    public List<Message> getMessagesForDiscussion(String discussionId) {
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
    }
}
