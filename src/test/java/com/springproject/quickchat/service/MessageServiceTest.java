package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.FileDTO;
import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.InMemoryMessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;
    private InMemoryMessageRepository messageRepository;
    private DiscussionService discussionService;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        DiscussionRepository discussionRepository = mock(DiscussionRepository.class);
        messageRepository = new InMemoryMessageRepository();
        discussionService = mock(DiscussionService.class);
        notificationService = mock(NotificationService.class);
        messageService = new MessageService(messageRepository, discussionService, notificationService);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("1", null, List.of())
        );

        messageRepository.addUser(1L, "Alice");
        messageRepository.addUser(2L, "Bob");
        messageRepository.addDiscussion(1L, 1L, 2L);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void sendMessage_validMessageWithoutFile_savesSuccessfully() {
        Long recipientId = 2L;
        String content = "Hello, how are you?";
        Long discussionId = 1L;

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionService.findOrCreateDiscussion(1L, recipientId)).thenReturn(discussionId);

        messageService.sendMessage(messageDTO, null);

        List<Message> messages = messageRepository.findByDiscussionId(discussionId);
        assertEquals(1, messages.size());
        assertEquals(content, messages.get(0).getContent());
    }

    @Test
    void sendMessage_validMessageWithFile_savesSuccessfully() {
        Long recipientId = 2L;
        String content = "Here is a file.";
        Long discussionId = 1L;

        MessageDTO messageDTO = new MessageDTO(recipientId, content);
        FileDTO fileDTO = new FileDTO("example.jpg", "image/jpeg", 150_000, "http://example.com/example.jpg");

        when(discussionService.findOrCreateDiscussion(1L, recipientId)).thenReturn(discussionId);

        messageService.sendMessage(messageDTO, fileDTO);

        List<Message> messages = messageRepository.findByDiscussionId(discussionId);
        assertEquals(1, messages.size());
        assertNotNull(messages.get(0).getAttachedFile());
        assertEquals("example.jpg", messages.get(0).getAttachedFile().getName());
    }

    @Test
    void sendMessage_invalidDiscussion_throwsException() {
        Long recipientId = 2L;
        String content = "This will fail.";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionService.findOrCreateDiscussion(1L, recipientId))
                .thenThrow(new IllegalArgumentException("Discussion introuvable."));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.sendMessage(messageDTO, null));

        assertEquals("Discussion introuvable.", exception.getMessage());
    }

    @Test
    void sendMessage_emptyContent_throwsException() {
        Long recipientId = 2L;
        String content = "   ";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionService.findOrCreateDiscussion(1L, recipientId)).thenReturn(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.sendMessage(messageDTO, null));

        assertEquals("Le contenu du message ne peut pas être vide.", exception.getMessage());
    }

    @Test
    void getMessagesForDiscussion_returnsValidMessages() {
        Long discussionId = 1L;

        Message message1 = Message.create(1L, discussionId, 1L, 2L, "Hello", LocalDateTime.now(), null);
        Message message2 = Message.create(2L, discussionId, 1L, 2L, "How are you?", LocalDateTime.now(), null);

        messageRepository.save(message1);
        messageRepository.save(message2);

        List<Message> messages = messageService.getMessagesForDiscussion(discussionId);

        assertEquals(2, messages.size());
    }

    @Test
    void sendMessage_fileExceedsSizeLimit_throwsException() {
        Long recipientId = 2L;
        String content = "Here is a large file.";
        Long discussionId = 1L;

        MessageDTO messageDTO = new MessageDTO(recipientId, content);
        FileDTO fileDTO = new FileDTO("largefile.mp4", "video/mp4", 250_000_000, "http://example.com/largefile.mp4");

        when(discussionService.findOrCreateDiscussion(1L, recipientId)).thenReturn(discussionId);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.sendMessage(messageDTO, fileDTO));

        assertEquals("Le fichier dépasse la taille maximale autorisée de 200 Mo.", exception.getMessage());
    }
}
