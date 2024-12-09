package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.Discussion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository {

    void save(Discussion discussion);

    List<Discussion> findAllByUserId(String userId);

    Discussion findById(String discussionId);

    @Query("SELECT d.id FROM DiscussionEntity d WHERE (d.user1 = :user1 AND d.user2 = :user2) OR (d.user1 = :user2 AND d.user2 = :user1)")
    String findDiscussionByUsers(@Param("user1") String user1, @Param("user2") String user2);
}
