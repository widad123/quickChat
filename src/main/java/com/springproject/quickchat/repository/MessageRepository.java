package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.Message;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository {
    void save(Message message);
}
