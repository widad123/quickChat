package com.springproject.quickchat.controller;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.service.FriendRequestService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    // Endpoint to send a friend request
    @PostMapping("/send")
    public ResponseEntity<FriendRequestEntity> sendFriendRequest(@RequestBody FriendRequest request ) {
        FriendRequestEntity savedRequest = friendRequestService.sendFriendRequest(request);
        return ResponseEntity.ok(savedRequest);
    }

    // Endpoint to get all friend requests
    @GetMapping
    public ResponseEntity<List<FriendRequestEntity>> getAllFriendRequests() {
        List<FriendRequestEntity> requests = friendRequestService.getAllFriendRequests();
        return ResponseEntity.ok(requests);
    }
}
