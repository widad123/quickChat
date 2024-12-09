package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.model.MessageContent;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.InMemoryMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;
    private InMemoryMessageRepository messageRepository;
    private DiscussionRepository discussionRepository;

    @BeforeEach
    void setUp() {
        discussionRepository = mock(DiscussionRepository.class);

        messageRepository = new InMemoryMessageRepository();

        messageService = new MessageService(messageRepository, discussionRepository);
    }

    @Test
    void sendMessage_validMessage_savesSuccessfully() {
        String senderId = "user1";
        String recipientId = "user2";
        String discussionId = "discussion1";
        String content = "Hello, how are you?";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionRepository.findDiscussionByUsers(senderId, recipientId)).thenReturn(discussionId);

        messageService.sendMessage(senderId, messageDTO);

        List<Message> messages = messageRepository.findMessagesByDiscussionId(discussionId);
        assertEquals(1, messages.size());
        Message savedMessage = messages.get(0);
        assertEquals(content, savedMessage.getContent());
        assertEquals(senderId, savedMessage.getSender());
        assertEquals(recipientId, savedMessage.getRecipient());
    }

    @Test
    void sendMessage_discussionDoesNotExist_throwsException() {
        String senderId = "user1";
        String recipientId = "user2";
        String content = "Hello, how are you?";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionRepository.findDiscussionByUsers(senderId, recipientId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.sendMessage(senderId, messageDTO)
        );
        assertEquals("La discussion entre les utilisateurs n'existe pas.", exception.getMessage());
    }

    @Test
    void sendMessage_emptyContent_throwsException() {
        String senderId = "user1";
        String recipientId = "user2";
        String discussionId = "discussion1";
        String content = "   ";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionRepository.findDiscussionByUsers(senderId, recipientId)).thenReturn(discussionId);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.sendMessage(senderId, messageDTO)
        );
        assertEquals("Le contenu du message ne peut pas être vide.", exception.getMessage());
    }

    @Test
    void sendMessage_exceedsMaxLength_throwsException() {
        String senderId = "user1";
        String recipientId = "user2";
        String discussionId = "discussion1";
        String content = "a".repeat(501);

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionRepository.findDiscussionByUsers(senderId, recipientId)).thenReturn(discussionId);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.sendMessage(senderId, messageDTO)
        );
        assertEquals("Le contenu du message ne doit pas dépasser 500 caractères.", exception.getMessage());
    }

    @Test
    void sendMessage_savesMessagesInDifferentDiscussions() {
        String senderId = "user1";
        String recipientId1 = "user2";
        String recipientId2 = "user3";

        String discussionId1 = "discussion1";
        String discussionId2 = "discussion2";

        when(discussionRepository.findDiscussionByUsers(senderId, recipientId1)).thenReturn(discussionId1);
        when(discussionRepository.findDiscussionByUsers(senderId, recipientId2)).thenReturn(discussionId2);

        MessageDTO message1 = new MessageDTO(recipientId1, "Message for recipient 1");
        MessageDTO message2 = new MessageDTO(recipientId2, "Message for recipient 2");

        messageService.sendMessage(senderId, message1);
        messageService.sendMessage(senderId, message2);

        List<Message> messagesForRecipient1 = messageRepository.findMessagesByDiscussionId(discussionId1);
        List<Message> messagesForRecipient2 = messageRepository.findMessagesByDiscussionId(discussionId2);

        assertEquals(1, messagesForRecipient1.size());
        assertEquals("Message for recipient 1", messagesForRecipient1.get(0).getContent());

        assertEquals(1, messagesForRecipient2.size());
        assertEquals("Message for recipient 2", messagesForRecipient2.get(0).getContent());
    }

    @Test
    void messageContent_validationWorks_correctly() {
        String validContent = "This is a valid message.";
        assertDoesNotThrow(() -> MessageContent.from(validContent));

        String emptyContent = "   ";
        IllegalArgumentException emptyException = assertThrows(IllegalArgumentException.class, () ->
                MessageContent.from(emptyContent)
        );
        assertEquals("Le contenu du message ne peut pas être vide.", emptyException.getMessage());

        String longContent = "a".repeat(501);
        IllegalArgumentException longException = assertThrows(IllegalArgumentException.class, () ->
                MessageContent.from(longContent)
        );
        assertEquals("Le contenu du message ne doit pas dépasser 500 caractères.", longException.getMessage());
    }
}
