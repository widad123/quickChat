package com.springproject.quickchat.repository;

import com.springproject.quickchat.model.FriendRequest;

import java.util.List;

public interface FriendRequestRepository {
    void sendFriendRequest(FriendRequest request);
    List<FriendRequest> getFriendRequests(String id);
}
