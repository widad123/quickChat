package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {
    boolean existsBySenderAndReceiver(UserEntity sender, UserEntity receiver);
    Optional<FriendRequestEntity> findById(Long id);

    Optional<FriendRequestEntity> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<FriendRequestEntity> findAllByReceiverId(Long receiverId);
}
