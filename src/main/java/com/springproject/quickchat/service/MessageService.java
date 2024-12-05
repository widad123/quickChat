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
        MessageDTO message = new MessageDTO(sender,recipient,content,LocalDateTime.now().toString());
        messages.add(message);
        return message;
    }

    public List<MessageDTO> getMessagesForRecipient(String recipient) {
        List<MessageDTO> recipientMessages = new ArrayList<>();
        for (MessageDTO message : messages) {
            if (message.recipient().equals(recipient)) {
                recipientMessages.add(message);
            }
        }
        return recipientMessages;
    }
}
