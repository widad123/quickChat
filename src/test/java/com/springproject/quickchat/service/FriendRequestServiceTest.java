package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.dto.FriendRequestDTO;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.InMemoryFriendRequestRepository;
import com.springproject.quickchat.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FriendRequestServiceTest {

    private FriendRequestService friendRequestService;
    private InMemoryFriendRequestRepository friendRequestRepository;
    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        friendRequestRepository = new InMemoryFriendRequestRepository();
        userRepository = new InMemoryUserRepository();
        friendRequestService = new FriendRequestService(friendRequestRepository, userRepository);

        // Adding test users
        userRepository.save(new UserEntity(null, "user1", "password1", "user1@example.com"));
        userRepository.save(new UserEntity(null, "user2", "password2", "user2@example.com"));
    }

    @Test
    void testSendFriendRequest() {
        FriendRequestDTO requestDTO = new FriendRequestDTO("user1", "user2");

        friendRequestService.sendFriendRequest(requestDTO);

        List<FriendRequest> requests = friendRequestService.getAllFriendRequests();
        assertEquals(1, requests.size());
        FriendRequest request = requests.get(0);

        assertEquals("user1", request.getFrom());
        assertEquals("user2", request.getTo());
        assertFalse(request.isAccepted());
        assertFalse(request.isDeclined());
    }

    @Test
    void testSendFriendRequest_UserNotFound() {
        FriendRequestDTO requestDTO = new FriendRequestDTO("user1", "unknown");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            friendRequestService.sendFriendRequest(requestDTO);
        });

        assertEquals("Receiver not found.", exception.getMessage());
    }

    @Test
    void testGetAllFriendRequests() {
        friendRequestService.sendFriendRequest(new FriendRequestDTO("user1", "user2"));

        List<FriendRequest> requests = friendRequestService.getAllFriendRequests();

        assertEquals(1, requests.size());
        assertEquals("user1", requests.get(0).getFrom());
        assertEquals("user2", requests.get(0).getTo());
    }
}
