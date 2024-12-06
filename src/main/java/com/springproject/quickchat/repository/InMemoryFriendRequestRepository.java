package com.springproject.quickchat.repository;


import com.springproject.quickchat.model.FriendRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryFriendRequestRepository implements FriendRequestRepository  {
    public final List<FriendRequest> friendRequests = new ArrayList<>();

    @Override
    public void sendFriendRequest(FriendRequest request) {
        friendRequests.add(request);
    }

    @Override
    public List<FriendRequest> getFriendRequests(String id) {
        return friendRequests.stream()
                .filter(request -> Objects.equals(request.getTo(), id))
                .collect(Collectors.toList());
    }
}