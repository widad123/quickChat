package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.DiscussionEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.dto.DiscussionDTO;
import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

    public DiscussionService(DiscussionRepository discussionRepository, UserRepository userRepository) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
    }

    public Long findOrCreateDiscussion(Long senderId, Long recipientId) {
        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found: " + senderId));

        UserEntity recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found: " + recipientId));

        return discussionRepository.findDiscussionByUsers(senderId, recipientId)
                .map(DiscussionEntity::getId)
                .orElseGet(() -> {
                    Discussion discussion = Discussion.create(senderId, recipientId);
                    DiscussionEntity entity = discussion.toEntity(sender, recipient);
                    return discussionRepository.save(entity).getId();
                });
    }

    public Discussion createDiscussion(DiscussionDTO dto) {
        UserEntity user1 = userRepository.findById(dto.getUser1Id())
                .orElseThrow(() -> new IllegalArgumentException("User1 not found: " + dto.getUser1Id()));
        UserEntity user2 = userRepository.findById(dto.getUser2Id())
                .orElseThrow(() -> new IllegalArgumentException("User2 not found: " + dto.getUser2Id()));

        Discussion discussion = Discussion.create(dto.getUser1Id(), dto.getUser2Id());
        DiscussionEntity entity = discussion.toEntity(user1, user2);

        discussionRepository.save(entity);
        return discussion;
    }
}
