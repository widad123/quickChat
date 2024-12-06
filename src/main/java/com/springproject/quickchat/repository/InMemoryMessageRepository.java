package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryMessageRepository implements MessageRepository {
    public final List<Message> messages = new ArrayList<>();

    @Override
    public void save(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> findAllByDiscussionId(String discussionId) {
        return messages.stream()
                .filter(message -> message.getDiscussionId().equals(discussionId))
                .collect(Collectors.toList());
    }

    @Override
    public Message findById(String messageId) {
        return messages.stream()
                .filter(message -> message.getId().equals(messageId))
                .findFirst()
                .orElse(null);
    }

}