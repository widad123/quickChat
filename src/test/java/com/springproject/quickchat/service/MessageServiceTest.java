package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.InMemoryDiscussionRepository;
import com.springproject.quickchat.repository.InMemoryMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {
    private MessageService messageService;
    private InMemoryMessageRepository messageRepository;
    private InMemoryDiscussionRepository discussionRepository;

    @BeforeEach
    void setUp() {
        messageRepository = new InMemoryMessageRepository();
        discussionRepository = new InMemoryDiscussionRepository();
        messageService = new MessageService(messageRepository, discussionRepository);
    }

    @Test
    void sendMessage_exceedsMaxLength_throwsException() {
        String content = "a".repeat(501);
        Message message = new Message("1", "1", "Alice", content, LocalDateTime.now().toString());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(message));
        assertEquals("Le message ne doit pas dépasser 500 caractères.", exception.getMessage());
    }

    @Test
    void sendMessage_emptyContent_throwsException() {
        Message message = new Message("1", "1", "Alice", "   ", LocalDateTime.now().toString());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(message));
        assertEquals("Le message ne peut pas être vide.", exception.getMessage());
    }

    @Test
    void sendMessage_discussionDoesNotExist_throwsException() {
        Message message = new Message("1", "nonexistent_discussion", "Alice", "Hello, Bob!", LocalDateTime.now().toString());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(message));
        assertEquals("La discussion spécifiée n'existe pas.", exception.getMessage());
    }

    @Test
    void sendMessage_validMessage_savesMessage() {
        discussionRepository.save(new Discussion("1", "Alice", "Bob"));
        Message message = new Message("1", "1", "Alice", "Hello, Bob!", LocalDateTime.now().toString());

        messageService.sendMessage(message);

        List<Message> messages = messageRepository.findAllByDiscussionId("1");
        assertTrue(messages.contains(message), "Le message n'est pas bien ajouté.");
        assertEquals(1, messages.size(), "Le nombre de messages dans la discussion est incorrect.");
    }

    @Test
    void editMessage_validContent_updatesMessage() {
        Message message = new Message("1", "1", "Alice", "Original content", LocalDateTime.now().toString());
        messageRepository.save(message);

        Message updatedMessage = messageService.editMessage("1", "Updated content");

        assertEquals("Updated content", updatedMessage.getContent(), "Le contenu du message n'a pas été mis à jour.");
        assertTrue(updatedMessage.isEdited(), "Le statut 'édité' devrait être vrai.");
    }

    @Test
    void editMessage_nonexistentMessage_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> messageService.editMessage("nonexistent", "New content"));
        assertEquals("Message introuvable ou déjà supprimé.", exception.getMessage());
    }


    @Test
    void editMessage_emptyContent_throwsException() {
        Message message = new Message("1", "1", "Alice", "Original content", LocalDateTime.now().toString());
        messageRepository.save(message);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> messageService.editMessage("1", "   "));
        assertEquals("Le contenu du message ne peut pas être vide.", exception.getMessage());
    }

    @Test
    void editMessage_exceedsMaxLength_throwsException() {
        Message message = new Message("1", "1", "Alice", "Original content", LocalDateTime.now().toString());
        messageRepository.save(message);

        String newContent = "a".repeat(501);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> messageService.editMessage("1", newContent));
        assertEquals("Le contenu du message ne doit pas dépasser 500 caractères.", exception.getMessage());
    }


}
