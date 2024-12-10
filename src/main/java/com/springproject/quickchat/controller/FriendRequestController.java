package com.springproject.quickchat.controller;

import com.springproject.quickchat.dto.FriendRequestDTO;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.service.FriendRequestService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/friend-requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDTO requestDTO) {
        friendRequestService.sendFriendRequest(requestDTO);
        return ResponseEntity.ok("Friend request sent successfully.");
    }

    @GetMapping
    public ResponseEntity<List<FriendRequest>> getAllFriendRequests() {
        List<FriendRequest> requests = friendRequestService.getAllFriendRequests();
        return ResponseEntity.ok(requests);
    }
}
