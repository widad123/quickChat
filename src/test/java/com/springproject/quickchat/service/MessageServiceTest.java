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
    void sendMessage_messageAddedToCorrectDiscussion() {
        discussionRepository.save(new Discussion("1", "Alice", "Bob"));

        Message message1 = new Message("1", "1", "Alice", "Hello, Bob!", LocalDateTime.now().toString());
        Message message2 = new Message("2", "1", "Bob", "Hi, Alice!", LocalDateTime.now().toString());

        messageService.sendMessage(message1);
        messageService.sendMessage(message2);

        List<Message> messages = messageRepository.findAllByDiscussionId("1");

        assertEquals(2, messages.size(), "Le nombre de messages pour la discussion est incorrect.");
        assertTrue(messages.contains(message1), "Le premier message n'est pas dans la discussion.");
        assertTrue(messages.contains(message2), "Le second message n'est pas dans la discussion.");
    }

    @Test
    void sendMessage_discussionDoesNotExist_messageNotSaved() {
        Message message = new Message("1", "nonexistent_discussion", "Alice", "Hello, Bob!", LocalDateTime.now().toString());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(message));

        assertEquals("La discussion spécifiée n'existe pas.", exception.getMessage());

        List<Message> messages = messageRepository.findAll();
        assertTrue(messages.isEmpty(), "Aucun message ne devrait être enregistré.");
    }

    @Test
    void sendMessage_messagesAreIsolatedByDiscussion() {
        discussionRepository.save(new Discussion("1", "Alice", "Bob"));
        discussionRepository.save(new Discussion("2", "Alice", "Charlie"));

        Message message1 = new Message("1", "1", "Alice", "Message to Bob", LocalDateTime.now().toString());
        Message message2 = new Message("2", "2", "Alice", "Message to Charlie", LocalDateTime.now().toString());

        messageService.sendMessage(message1);
        messageService.sendMessage(message2);

        List<Message> messagesForBob = messageRepository.findAllByDiscussionId("1");
        List<Message> messagesForCharlie = messageRepository.findAllByDiscussionId("2");

        assertEquals(1, messagesForBob.size(), "Le nombre de messages pour Bob est incorrect.");
        assertEquals(1, messagesForCharlie.size(), "Le nombre de messages pour Charlie est incorrect.");

        assertTrue(messagesForBob.contains(message1), "Le message pour Bob n'est pas dans la discussion.");
        assertTrue(messagesForCharlie.contains(message2), "Le message pour Charlie n'est pas dans la discussion.");
    }



}
