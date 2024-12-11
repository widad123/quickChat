package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.dto.FriendRequestDTO;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.FriendRequestRepository;
import com.springproject.quickchat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public FriendRequestService(FriendRequestRepository friendRequestRepository,
                                UserRepository userRepository,
                                NotificationService notificationService) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }


    public void sendFriendRequest(FriendRequestDTO requestDTO) {
        UserEntity sender = userRepository.findByUsername(requestDTO.from())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found."));

        UserEntity receiver = userRepository.findByUsername(requestDTO.to())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found."));

        boolean requestExists = friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
        if (requestExists) {
            throw new IllegalStateException("A friend request already exists between these users.");
        }

        FriendRequest request = new FriendRequest(requestDTO.from(), requestDTO.to(), LocalDateTime.now());
        FriendRequestEntity entity = new FriendRequestEntity();
        entity.setSender(sender);
        entity.setReceiver(receiver);
        entity.setStatus("PENDING");
        entity.setCreatedAt(request.getCreatedAt());

        friendRequestRepository.save(entity);
        notificationService.sendNotification(
                receiver.getId(),
                "friend-request-received",
                "Vous avez reçu une demande d’ami de " + sender.getUsername()
        );
    }

    public List<FriendRequest> getAllFriendRequests() {
        return friendRequestRepository.findAll()
                .stream()
                .map(entity -> new FriendRequest(
                        entity.getSender().getUsername(),
                        entity.getReceiver().getUsername(),
                        entity.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public void acceptFriendRequest(Long requestId) {
        FriendRequestEntity request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found: " + requestId));

        if ("ACCEPTED".equals(request.getStatus())) {
            throw new IllegalStateException("Friend request is already accepted.");
        }

        request.setStatus("ACCEPTED");
        friendRequestRepository.save(request);

        UserEntity sender = request.getSender();
        UserEntity receiver = request.getReceiver();
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);

        userRepository.save(sender);
        userRepository.save(receiver);

        notificationService.sendNotification(
                sender.getId(),
                "friend-request-accepted",
                "Votre demande d’ami à " + receiver.getUsername() + " a été acceptée."
        );
    }

    public void cancelFriendRequest(Long senderId, Long receiverId) {
        FriendRequestEntity request = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));

        friendRequestRepository.delete(request);

        notificationService.sendNotification(
                receiverId,
                "friend-request-canceled",
                "L'utilisateur avec l'ID " + senderId + " a annulé sa demande d'ami."
        );
    }

    public void declineFriendRequest(Long receiverId, Long senderId) {
        FriendRequestEntity request = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));

        friendRequestRepository.delete(request);

        notificationService.sendNotification(
                senderId,
                "friend-request-declined",
                "Votre demande d’ami a été refusée par l'utilisateur avec l'ID " + receiverId + "."
        );
    }

    public List<FriendRequestDTO> listReceivedFriendRequests(Long receiverId) {
        List<FriendRequestEntity> requests = friendRequestRepository.findAllByReceiverId(receiverId);
        return requests.stream()
                .map(request -> new FriendRequestDTO(request.getSender().getUsername(), request.getReceiver().getUsername()))
                .collect(Collectors.toList());
    }



}