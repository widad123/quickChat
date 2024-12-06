package com.springproject.quickchat.service;

import com.springproject.quickchat.model.MessageDTO;
import com.springproject.quickchat.repository.InMemoryMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {

    private MessageService messageService;
    InMemoryMessageRepository messageRepository = new InMemoryMessageRepository();

    @BeforeEach
    void setup() {
        messageService = new MessageService(messageRepository);
    }
    @Test
    void sendMessage() {
        MessageDTO messageDTO = new MessageDTO("Alice", "Bob", "Hello, Bob!", LocalDateTime.now().toString());
        messageService.sendMessage(messageDTO);
        assertTrue(messageRepository.messages.contains(messageDTO), "");
    }
}