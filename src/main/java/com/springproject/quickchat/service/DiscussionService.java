package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.repository.DiscussionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

    public DiscussionService(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    public void createDiscussion(String user1, String user2) {
        Discussion discussion = new Discussion(
                String.valueOf(System.currentTimeMillis()),
                user1,
                user2
        );
        discussionRepository.save(discussion);
    }

    public List<Discussion> getDiscussionsForUser(String userId) {
        return discussionRepository.findAllByUserId(userId);
    }
}
