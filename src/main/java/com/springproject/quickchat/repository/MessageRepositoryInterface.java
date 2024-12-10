package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepositoryInterface {
    void save(Message message);
    Optional<Message> findById(Long messageId);
    List<Message> findByDiscussionId(Long discussionId);
}
