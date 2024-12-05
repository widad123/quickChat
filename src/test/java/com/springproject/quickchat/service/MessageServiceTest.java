package com.springproject.quickchat.service;

import com.springproject.quickchat.model.MessageDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {

    @Test
    void sendMessage() {
        MessageService messageService = new MessageService();
        MessageDTO message = messageService.sendMessage("Alice", "Bob", "Hello, Bob!");

        assertNotNull(message);
        assertEquals("Alice", message.getSender());
        assertEquals("Bob", message.getRecipient());
        assertEquals("Hello, Bob!", message.getContent());
    }
}