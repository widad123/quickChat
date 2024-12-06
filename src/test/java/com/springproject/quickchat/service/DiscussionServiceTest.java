package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.InMemoryDiscussionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiscussionServiceTest {
    private DiscussionService discussionService;
    private DiscussionRepository discussionRepository;

    @BeforeEach
    void setup() {
        discussionRepository = new InMemoryDiscussionRepository();
        discussionService = new DiscussionService(discussionRepository);
    }

    @Test
    void createDiscussion() {
        discussionService.createDiscussion("Alice", "Bob");

        List<Discussion> discussions = discussionRepository.findAllByUserId("Alice");

        assertEquals(1, discussions.size(), "Le nombre de discussions est incorrect.");
        Discussion discussion = discussions.get(0);
        assertEquals("Alice", discussion.getUser1(), "L'utilisateur 1 est incorrect.");
        assertEquals("Bob", discussion.getUser2(), "L'utilisateur 2 est incorrect.");
    }

    @Test
    void getDiscussionsForUser() {
        discussionService.createDiscussion("Alice", "Bob");
        discussionService.createDiscussion("Alice", "Charlie");
        discussionService.createDiscussion("Bob", "Charlie");

        List<Discussion> aliceDiscussions = discussionRepository.findAllByUserId("Alice");
        List<Discussion> bobDiscussions = discussionRepository.findAllByUserId("Bob");

        assertEquals(2, aliceDiscussions.size(), "Le nombre de discussions pour Alice est incorrect.");
        assertEquals(2, bobDiscussions.size(), "Le nombre de discussions pour Bob est incorrect.");

        assertTrue(aliceDiscussions.stream().anyMatch(d -> d.getUser2().equals("Bob")));
        assertTrue(aliceDiscussions.stream().anyMatch(d -> d.getUser2().equals("Charlie")));

        assertTrue(bobDiscussions.stream().anyMatch(d -> d.getUser2().equals("Charlie")));
        assertTrue(bobDiscussions.stream().anyMatch(d -> d.getUser1().equals("Alice")));
    }
}
