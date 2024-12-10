package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {
    default FriendRequestEntity saveFriendRequest(Long senderId, Long receiverId) {
        FriendRequestEntity entity = new FriendRequestEntity();
        entity.setSenderId(senderId);
        entity.setReceiverId(receiverId);
        entity.setStatus("PENDING");
        entity.setCreatedAt(LocalDateTime.now());

        return save(entity);
    }
}
