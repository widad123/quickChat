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
    public ResponseEntity<String> sendFriendRequest(@RequestParam String recipient) {
        friendRequestService.sendFriendRequest(recipient);
        return ResponseEntity.ok("Friend request sent successfully.");
    }

    @GetMapping
    public ResponseEntity<List<FriendRequest>> getAllFriendRequests() {
        List<FriendRequest> requests = friendRequestService.getAllFriendRequests();
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long id) {
        friendRequestService.acceptFriendRequest(id);
        return ResponseEntity.ok("Friend request accepted successfully.");
    }

    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestDTO>> listReceivedFriendRequests() {
        List<FriendRequestDTO> requests = friendRequestService.listReceivedFriendRequests();
        return ResponseEntity.ok(requests);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelFriendRequest(
            @RequestParam Long receiverId) {
        friendRequestService.cancelFriendRequest(receiverId);
        return ResponseEntity.ok("Friend request canceled successfully.");
    }

    @DeleteMapping("/decline")
    public ResponseEntity<String> declineFriendRequest(
            @RequestParam Long senderId) {
        friendRequestService.declineFriendRequest(senderId);
        return ResponseEntity.ok("Friend request declined successfully.");
    }

}
