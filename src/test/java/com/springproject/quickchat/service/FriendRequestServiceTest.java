package com.springproject.quickchat.service;

import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.InMemoryFriendRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendRequestServiceTest {
    FriendRequestService friendRequestService;
    InMemoryFriendRequestRepository repository = new InMemoryFriendRequestRepository();

    @BeforeEach
    void setUp() {
        friendRequestService = new FriendRequestService(repository);
    }
    @Test
    void sendFriendRequest() {
        FriendRequest friendRequest = new FriendRequest("Ayyoub","Asmae");
        friendRequestService.sendFriendRequest(friendRequest);
        assertTrue(this.repository.friendRequests.contains(friendRequest),"La demande n'a pas été envoyé");
    }
    @Test
    void getFriendRequests() {
        FriendRequest friendRequest1 = new FriendRequest("Ayyoub","Asmae");
        FriendRequest friendRequest2 = new FriendRequest("Widad","Ayyoub");
        friendRequestService.sendFriendRequest(friendRequest1);
        friendRequestService.sendFriendRequest(friendRequest2);
        List<FriendRequest> resultFriendRequest = new ArrayList<>();
        resultFriendRequest.add(friendRequest1);
        assertEquals(this.repository.getFriendRequests("Asmae"), resultFriendRequest, "La demande n'a pas été envoyé");
    }

}