package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    // Send a new friend request
    public FriendRequestEntity sendFriendRequest(FriendRequest request) {
        return friendRequestRepository.saveFriendRequest(request.getSenderId(), request.getReceiverId());
    }

    // Get all friend requests
    public List<FriendRequestEntity> getAllFriendRequests() {
        return friendRequestRepository.findAll();
    }
}
