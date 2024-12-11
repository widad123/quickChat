package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.dto.FriendRequestDTO;
import com.springproject.quickchat.repository.FriendRequestRepository;
import com.springproject.quickchat.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FriendRequestServiceTest {

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FriendRequestService friendRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("1", null, List.of())
        );
    }


    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testSendFriendRequest() {
        Long senderId = 1L;
        Long receiverId = 2L;

        UserEntity sender = new UserEntity();
        sender.setId(senderId);
        sender.setUsername("Alice");

        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);
        receiver.setUsername("Bob");

        when(userRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(userRepository.findByUsername("Bob")).thenReturn(Optional.of(receiver));
        when(friendRequestRepository.existsBySenderAndReceiver(sender, receiver)).thenReturn(false);

        friendRequestService.sendFriendRequest(receiver.getUsername());

        verify(friendRequestRepository).save(any(FriendRequestEntity.class));
        verify(notificationService).sendNotification(eq(receiverId), eq("friend-request-received"), anyString());
    }

    @Test
    void testSendFriendRequest_UserNotFound() {
        Long senderId = 1L;
        Long receiverId = 2L;
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        UserEntity sender = new UserEntity();
        sender.setId(senderId);
        sender.setUsername("Alice");

        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);
        receiver.setUsername("Bob");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                friendRequestService.sendFriendRequest(receiver.getUsername())
        );
        assertEquals("Sender not found.", exception.getMessage());
    }

    @Test
    void testGetAllFriendRequests() {
        Long receiverId = 1L;

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(receiverId.toString(), null, List.of())
        );

        UserEntity sender = new UserEntity();
        sender.setId(2L);
        sender.setUsername("Alice");

        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);
        receiver.setUsername("Bob");

        FriendRequestEntity requestEntity = new FriendRequestEntity();
        requestEntity.setSender(sender);
        requestEntity.setReceiver(receiver);

        List<FriendRequestEntity> requestEntities = List.of(requestEntity);

        when(friendRequestRepository.findAllByReceiverId(receiverId)).thenReturn(requestEntities);

        List<FriendRequestDTO> result = friendRequestService.listReceivedFriendRequests();

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).from());
        assertEquals("Bob", result.get(0).to());
    }

    @Test
    void testCancelFriendRequest() {
        Long senderId = 1L;
        Long receiverId = 2L;

        FriendRequestEntity requestEntity = new FriendRequestEntity();
        requestEntity.setSender(new UserEntity());
        requestEntity.setReceiver(new UserEntity());

        when(friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId))
                .thenReturn(Optional.of(requestEntity));

        friendRequestService.cancelFriendRequest(receiverId);

        verify(friendRequestRepository).delete(requestEntity);
        verify(notificationService).sendNotification(eq(receiverId), eq("friend-request-canceled"), anyString());
    }

    @Test
    void testCancelFriendRequest_NotFound() {
        Long receiverId = 2L;

        when(friendRequestRepository.findBySenderIdAndReceiverId(1L, receiverId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> friendRequestService.cancelFriendRequest(receiverId));
    }

    @Test
    void testDeclineFriendRequest() {
        Long senderId = 1L;
        Long receiverId = 2L;

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(receiverId.toString(), null, List.of())
        );

        UserEntity sender = new UserEntity();
        sender.setId(senderId);
        sender.setUsername("Alice");

        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);
        receiver.setUsername("Bob");

        FriendRequestEntity requestEntity = new FriendRequestEntity();
        requestEntity.setSender(sender);
        requestEntity.setReceiver(receiver);

        when(friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId))
                .thenReturn(Optional.of(requestEntity));

        friendRequestService.declineFriendRequest(senderId);

        verify(friendRequestRepository).delete(requestEntity);
        verify(notificationService).sendNotification(eq(senderId), eq("friend-request-declined"), anyString());
    }


    @Test
    void testAcceptFriendRequest() {
        Long requestId = 1L;
        Long receiverId = 1L;

        FriendRequestEntity requestEntity = new FriendRequestEntity();
        UserEntity sender = new UserEntity();
        sender.setId(2L);
        sender.setUsername("Bob");
        sender.setFriends(new ArrayList<>());

        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);
        receiver.setUsername("Alice");
        receiver.setFriends(new ArrayList<>());

        requestEntity.setSender(sender);
        requestEntity.setReceiver(receiver);

        when(friendRequestRepository.findById(requestId)).thenReturn(Optional.of(requestEntity));
        when(userRepository.save(sender)).thenReturn(sender);
        when(userRepository.save(receiver)).thenReturn(receiver);

        friendRequestService.acceptFriendRequest(requestId);

        verify(friendRequestRepository).save(requestEntity);
        verify(notificationService).sendNotification(eq(sender.getId()), eq("friend-request-accepted"), anyString());
    }


    @Test
    void testAcceptFriendRequest_NotFound() {
        Long requestId = 1L;

        when(friendRequestRepository.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> friendRequestService.acceptFriendRequest(requestId));
    }
}
