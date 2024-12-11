package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.dto.FriendRequestDTO;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.FriendRequestRepository;
import com.springproject.quickchat.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof String) {
            try {
                return Long.valueOf(authentication.getPrincipal().toString());
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Le principal n'est pas un ID utilisateur valide : " + authentication.getPrincipal());
            }
        }
        throw new IllegalStateException("Utilisateur non authentifié ou principal invalide.");
    }

    public void sendFriendRequest(String recipient) {
        Long senderId = getCurrentUserId();
        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found."));

        UserEntity receiver = userRepository.findByUsername(recipient)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found."));

        boolean requestExists = friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
        if (requestExists) {
            throw new IllegalStateException("A friend request already exists between these users.");
        }

        FriendRequest request = new FriendRequest(sender.getUsername(), recipient, LocalDateTime.now());
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

    public void cancelFriendRequest(Long receiverId) {
        Long senderId = getCurrentUserId();
        FriendRequestEntity request = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));

        friendRequestRepository.delete(request);

        notificationService.sendNotification(
                receiverId,
                "friend-request-canceled",
                "L'utilisateur avec l'ID " + senderId + " a annulé sa demande d'ami."
        );
    }

    public void declineFriendRequest(Long senderId) {
        Long receiverId = getCurrentUserId();
        FriendRequestEntity request = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));

        friendRequestRepository.delete(request);

        notificationService.sendNotification(
                senderId,
                "friend-request-declined",
                "Votre demande d’ami a été refusée par l'utilisateur avec l'ID " + receiverId + "."
        );
    }

    public List<FriendRequestDTO> listReceivedFriendRequests() {
        Long receiverId = getCurrentUserId();
        List<FriendRequestEntity> requests = friendRequestRepository.findAllByReceiverId(receiverId);
        return requests.stream()
                .map(request -> new FriendRequestDTO(request.getSender().getUsername(), request.getReceiver().getUsername()))
                .collect(Collectors.toList());
    }


    public void acceptFriendRequest(Long requestId) {
        Long currentUserId = getCurrentUserId();
        FriendRequestEntity request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found: " + requestId));

        if (!request.getReceiver().getId().equals(currentUserId)) {
            throw new SecurityException("Vous ne pouvez pas accepter une demande d'ami qui ne vous est pas adressée.");
        }

        if ("ACCEPTED".equals(request.getStatus())) {
            throw new IllegalStateException("Friend request is already accepted.");
        }

        request.setStatus("ACCEPTED");
        friendRequestRepository.save(request);

        UserEntity sender = request.getSender();
        UserEntity receiver = request.getReceiver();

        if (sender.getFriends() == null) {
            sender.setFriends(new ArrayList<>());
        }
        if (receiver.getFriends() == null) {
            receiver.setFriends(new ArrayList<>());
        }
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
}
