package com.springproject.quickchat.service;

import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.FriendRequestRepository;

import java.util.List;

public class FriendRequestService {
    private FriendRequestRepository friendRequestRepository ;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public void sendFriendRequest(FriendRequest request) {
        this.friendRequestRepository.sendFriendRequest(request);
    }
    public List<FriendRequest> getFriendRequests(String idRecipient) {
        return this.friendRequestRepository.getFriendRequests(idRecipient);
    }
}
