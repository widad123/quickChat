package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.Discussion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository {
    void save(Discussion discussion);
    List<Discussion> findAllByUserId(String userId);
    Discussion findById(String discussionId);
}
