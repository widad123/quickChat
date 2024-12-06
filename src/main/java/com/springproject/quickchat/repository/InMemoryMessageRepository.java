package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.MessageDTO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageRepository implements MessageRepository {
    public final List<MessageDTO> messages = new ArrayList<>();

    @Override
    public void save(MessageDTO message) {
        messages.add(message);
    }

}