package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.Message;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository{
    void save(Message message);

    List<Message> findAllByDiscussionId(String discussionId);

    Message findById(String messageId);
}
