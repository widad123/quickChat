package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.MessageDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository {
    void save(MessageDTO message);
}
