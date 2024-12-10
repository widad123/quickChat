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

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }

    public void sendFriendRequest(FriendRequestDTO requestDTO) {
        UserEntity sender = userRepository.findByUsername(requestDTO.from())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found."));

        UserEntity receiver = userRepository.findByUsername(requestDTO.to())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found."));

        FriendRequest request = new FriendRequest(requestDTO.from(), requestDTO.to(), LocalDateTime.now());
        FriendRequestEntity entity = new FriendRequestEntity();
        entity.setSender(sender);
        entity.setReceiver(receiver);
        entity.setStatus("PENDING");
        entity.setCreatedAt(request.getCreatedAt());

        friendRequestRepository.save(entity);
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
}