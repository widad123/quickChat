package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Long> {
}
