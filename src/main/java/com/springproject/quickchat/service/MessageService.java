package com.springproject.quickchat.service;

import com.springproject.quickchat.model.MessageDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final List<MessageDTO> messages = new ArrayList<>();

    public MessageDTO sendMessage(String sender, String recipient, String content) {
        MessageDTO message = new MessageDTO();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now().toString());
        messages.add(message);
        return message;
    }

    public List<MessageDTO> getMessagesForRecipient(String recipient) {
        List<MessageDTO> recipientMessages = new ArrayList<>();
        for (MessageDTO message : messages) {
            if (message.getRecipient().equals(recipient)) {
                recipientMessages.add(message);
            }
        }
        return recipientMessages;
    }
}
