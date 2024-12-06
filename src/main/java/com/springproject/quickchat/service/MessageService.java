package com.springproject.quickchat.service;

import com.springproject.quickchat.model.MessageDTO;
import com.springproject.quickchat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(MessageDTO message) {
        messageRepository.save(message);
    }

}
