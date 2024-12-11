package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.DiscussionEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.dto.DiscussionDTO;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
                    DiscussionEntity entity = new DiscussionEntity();
                    entity.setParticipant1(sender);
                    entity.setParticipant2(recipient);
                    entity.setTitle("Conversation entre " + sender.getUsername() + " et " + recipient.getUsername());
                    return discussionRepository.save(entity).getId();
                });
    }

    public DiscussionEntity createDiscussion(DiscussionDTO dto) {
        UserEntity user1 = userRepository.findById(dto.getUser1Id())
                .orElseThrow(() -> new IllegalArgumentException("User1 not found: " + dto.getUser1Id()));
        UserEntity user2 = userRepository.findById(dto.getUser2Id())
                .orElseThrow(() -> new IllegalArgumentException("User2 not found: " + dto.getUser2Id()));

        DiscussionEntity entity = new DiscussionEntity();
        entity.setParticipant1(user1);
        entity.setParticipant2(user2);
        entity.setTitle(dto.getTitle());

        return discussionRepository.save(entity);
    }

    public List<DiscussionDTO> getUserDiscussions(Long userId) {
        List<DiscussionEntity> discussions = discussionRepository.findDiscussionsByUserId(userId);
        return discussions.stream()
                .map(discussion -> new DiscussionDTO(
                        discussion.getId(),
                        discussion.getParticipant1().getId(),
                        discussion.getParticipant2().getId(),
                        discussion.getTitle()
                ))
                .collect(Collectors.toList());
    }
}
