package com.springproject.quickchat.service;

import com.springproject.quickchat.dto.FileDTO;
import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;
    private InMemoryMessageRepository messageRepository;
    private DiscussionService discussionService;

    @BeforeEach
    void setUp() {
        messageRepository = mock(InMemoryMessageRepository.class);
        discussionService = mock(DiscussionService.class);

        MessageLogRepository messageLogRepository = mock(MessageLogRepository.class);
        FileLogRepository fileLogRepository = mock(FileLogRepository.class);
        messageService = new MessageService(messageRepository, discussionService, messageLogRepository, fileLogRepository);

        messageRepository.addUser(1L, "Alice");
        messageRepository.addUser(2L, "Bob");
        messageRepository.addDiscussion(1L, 1L, 2L);

    }

    @Test
    void sendMessage_validMessageWithoutFile_savesSuccessfully() {
        Long senderId = 1L;
        Long recipientId = 2L;
        String content = "Hello, how are you?";
        Long discussionId = 1L;
        List<Message> savedMessages = new ArrayList<>();
        doAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            savedMessages.add(message);
            return message;
        }).when(messageRepository).save(any(Message.class));

        when(messageRepository.findByDiscussionId(discussionId)).thenReturn(savedMessages);


        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionService.findOrCreateDiscussion(senderId, recipientId)).thenReturn(discussionId);

        messageService.sendMessage(senderId, messageDTO, null);

        List<Message> messages = messageRepository.findByDiscussionId(discussionId);
        assertEquals(1, messages.size());
        assertEquals(content, messages.get(0).getContent());
    }

    @Test
    void sendMessage_validMessageWithFile_savesSuccessfully() {
        Long senderId = 1L;
        Long recipientId = 2L;
        String content = "Here is a file.";
        Long discussionId = 1L;

        MessageDTO messageDTO = new MessageDTO(recipientId, content);
        FileDTO fileDTO = new FileDTO("example.jpg", "image/jpeg", 150_000, "http://example.com/example.jpg");

        when(discussionService.findOrCreateDiscussion(senderId, recipientId)).thenReturn(discussionId);

        List<Message> savedMessages = new ArrayList<>();
        doAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            savedMessages.add(message);
            return message;
        }).when(messageRepository).save(any(Message.class));

        when(messageRepository.findByDiscussionId(discussionId)).thenReturn(savedMessages);

        messageService.sendMessage(senderId, messageDTO, fileDTO);

        List<Message> messages = messageRepository.findByDiscussionId(discussionId);
        assertEquals(1, messages.size());
        assertNotNull(messages.get(0).getAttachedFile());
        assertEquals("example.jpg", messages.get(0).getAttachedFile().getName());
    }


    @Test
    void sendMessage_invalidDiscussion_throwsException() {
        Long senderId = 1L;
        Long recipientId = 2L;
        String content = "This will fail.";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionService.findOrCreateDiscussion(senderId, recipientId))
                .thenThrow(new IllegalArgumentException("Discussion introuvable."));

        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(senderId, messageDTO, null));
    }

    @Test
    void sendMessage_emptyContent_throwsException() {
        Long senderId = 1L;
        Long recipientId = 2L;
        Long discussionId = 1L;
        String content = "   ";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);

        when(discussionService.findOrCreateDiscussion(senderId, recipientId)).thenReturn(discussionId);

        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(senderId, messageDTO, null));
    }

    @Test
    void getMessagesForDiscussion_returnsValidMessages() {
        Long discussionId = 1L;

        Message message1 = Message.create(1L, discussionId, 1L, 2L, "Hello", LocalDateTime.now(), null);
        Message message2 = Message.create(2L, discussionId, 1L, 2L, "How are you?", LocalDateTime.now(), null);

        List<Message> mockMessages = List.of(message1, message2);

        when(messageRepository.findByDiscussionId(discussionId)).thenReturn(mockMessages);

        List<Message> messages = messageService.getMessagesForDiscussion(discussionId);

        assertEquals(2, messages.size());
        assertEquals("Hello", messages.get(0).getContent());
        assertEquals("How are you?", messages.get(1).getContent());
    }


    @Test
    void editMessage_validMessage_updatesContent() {
        Long messageId = 1L;
        String newContent = "Updated content";

        Message message = Message.create(messageId, 1L, 1L, 2L, "Original content", LocalDateTime.now(), null);

        when(messageRepository.findById(messageId)).thenReturn(java.util.Optional.of(message));

        Message updatedMessage = messageService.editMessage(messageId, newContent);

        assertEquals(newContent, updatedMessage.getContent());
        assertTrue(updatedMessage.isEdited());
        verify(messageRepository).save(message);
    }

    @Test
    void editMessage_deletedMessage_throwsException() {
        Long messageId = 1L;
        Long discussionId = 1L;
        Long senderId = 1L;
        Long recipientId = 2L;

        Message message = Message.create(
                messageId,
                discussionId,
                senderId,
                recipientId,
                "Original content",
                LocalDateTime.now(),
                null
        );
        message.markAsDeleted();

        when(messageRepository.findById(messageId)).thenReturn(java.util.Optional.of(message));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> messageService.editMessage(messageId, "Updated content"));

        assertEquals("Impossible d'éditer un message supprimé.", exception.getMessage());
        assertTrue(message.isDeleted(), "Le message doit toujours être marqué comme supprimé.");
        verify(messageRepository, never()).save(message);
    }



    @Test
    void sendMessage_fileExceedsSizeLimit_throwsException() {
        Long senderId = 1L;
        Long recipientId = 2L;
        Long discussionId = 1L;
        String content = "Here is a large file.";

        MessageDTO messageDTO = new MessageDTO(recipientId, content);
        FileDTO fileDTO = new FileDTO("largefile.mp4", "video/mp4", 250_000_000, "http://example.com/largefile.mp4");

        when(discussionService.findOrCreateDiscussion(senderId, recipientId)).thenReturn(discussionId);

        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(senderId, messageDTO, fileDTO));
    }

    @Test
    void getMessages_emptyDiscussion_returnsEmptyList() {
        Long discussionId = 1L;
        List<Message> messages = messageService.getMessagesForDiscussion(discussionId);
        assertTrue(messages.isEmpty());
    }
}
