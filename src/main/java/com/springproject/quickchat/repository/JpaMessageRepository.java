package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByDiscussion_Id(Long discussionId);
}
