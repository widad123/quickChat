package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.Discussion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryDiscussionRepository implements DiscussionRepository {
    private final List<Discussion> discussions = new ArrayList<>();

    @Override
    public void save(Discussion discussion) {
        discussions.add(discussion);
    }

    @Override
    public List<Discussion> findAllByUserId(String userId) {
        return discussions.stream()
                .filter(discussion -> discussion.getUser1().equals(userId) || discussion.getUser2().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Discussion findById(String discussionId) {
        return discussions.stream()
                .filter(discussion -> discussion.getId().equals(discussionId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String findDiscussionByUsers(String user1, String user2) {
        return "";
    }

}
