package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {

    @Test
    void sendMessage() {
        MessageService messageService = new MessageService();
        Message message = messageService.sendMessage("1", "Alice", "Hello, Bob!");

        assertNotNull(message, "Le message ne doit pas être nul.");
        assertEquals("Alice", message.getSender(), "L'expéditeur du message est incorrect.");
        assertEquals("1", message.getDiscussionId(), "L'ID de la discussion est incorrect.");
        assertEquals("Hello, Bob!", message.getContent(), "Le contenu du message est incorrect.");
        assertNotNull(message.getTimestamp(), "Le timestamp ne doit pas être nul.");
    }
}