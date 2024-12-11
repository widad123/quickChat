package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.config.SecurityUtils;
import com.springproject.quickchat.dto.FriendRequestDto;
import com.springproject.quickchat.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public void sendFriendRequest(FriendRequestDto friendRequestDto) {
        Long senderId = SecurityUtils.getAuthenticatedUserId();

        FriendRequestEntity friendRequest = new FriendRequestEntity();
        friendRequest.setSenderId(senderId);
        friendRequest.setReceiverId(friendRequestDto.getReceiverId());

        friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequestEntity> getReceivedFriendRequests() {
        Long receiverId = SecurityUtils.getAuthenticatedUserId(); // ID du destinataire connecté
        return friendRequestRepository.findByReceiverId(receiverId);
    }
}
