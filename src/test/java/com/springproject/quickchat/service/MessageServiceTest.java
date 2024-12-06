package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.InMemoryMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {
    MessageService messageService;
    InMemoryMessageRepository repository = new InMemoryMessageRepository();

    @BeforeEach
    void setUp() {
        messageService = new MessageService(repository);
    }
    @Test
    void sendMessage() {
        Message message = new Message("1", "Alice", "Hello, Bob!", LocalDateTime.now().toString());
        messageService.sendMessage(message);
        assertTrue(this.repository.messages.contains(message),"Le message n'est pas bien ajouté");
    }
}