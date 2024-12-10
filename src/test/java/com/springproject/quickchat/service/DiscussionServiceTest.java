package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.DiscussionEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.repository.InMemoryDiscussionRepository;
import com.springproject.quickchat.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscussionServiceTest {

    private InMemoryDiscussionRepository discussionRepository;
    private UserRepository userRepository;
    private DiscussionService discussionService;

    @BeforeEach
    void setUp() {
        discussionRepository = new InMemoryDiscussionRepository();
        userRepository = mock(UserRepository.class);
        discussionService = new DiscussionService(discussionRepository, userRepository);
    }

    @Test
    void testFindOrCreateDiscussion_whenDiscussionExists() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        DiscussionEntity discussion = new DiscussionEntity();
        discussion.setId(1L);
        discussion.setParticipant1(user1);
        discussion.setParticipant2(user2);

        discussionRepository.save(discussion);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        Long discussionId = discussionService.findOrCreateDiscussion(1L, 2L);

        assertEquals(1L, discussionId);
    }

    @Test
    void testFindOrCreateDiscussion_whenDiscussionDoesNotExist() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        Long discussionId = discussionService.findOrCreateDiscussion(1L, 2L);

        assertNotNull(discussionId);
        Optional<DiscussionEntity> discussionEntity = discussionRepository.findById(discussionId);
        assertTrue(discussionEntity.isPresent());
        assertEquals(user1.getId(), discussionEntity.get().getParticipant1().getId());
        assertEquals(user2.getId(), discussionEntity.get().getParticipant2().getId());
    }

    @Test
    void testFindOrCreateDiscussion_whenUserNotFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> discussionService.findOrCreateDiscussion(1L, 2L));
        assertEquals("Sender not found: 1", exception.getMessage());
    }

    @Test
    void testCreateDiscussion() {
        InMemoryDiscussionRepository repository = new InMemoryDiscussionRepository();
        DiscussionEntity discussion = new DiscussionEntity();
        discussion.setTitle("Test Discussion");

        DiscussionEntity saved = repository.save(discussion);

        assertNotNull(saved.getId(), "ID should be generated");
        assertEquals("Test Discussion", saved.getTitle());
    }

}