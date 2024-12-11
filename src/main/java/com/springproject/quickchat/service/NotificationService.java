package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.DiscussionEntity;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.SseEmitterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
public class NotificationService {

    private final SseEmitterRepository sseEmitterRepository;
    private final DiscussionRepository discussionRepository;

    public NotificationService(SseEmitterRepository sseEmitterRepository, DiscussionRepository discussionRepository) {
        this.sseEmitterRepository = sseEmitterRepository;
        this.discussionRepository = discussionRepository;
    }

    public void sendNotificationToDiscussion(Long discussionId, String eventType, String message) {
        List<Long> participantIds = getParticipantIdsForDiscussion(discussionId);
        for (Long userId : participantIds) {
            sendNotification(userId, eventType, message);
        }
    }

    public void sendNotification(Long userId, String eventType, String message) {
        SseEmitter emitter = sseEmitterRepository.getEmitterForUser(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventType)
                        .data(message));
            } catch (IOException e) {
                emitter.completeWithError(e);
                sseEmitterRepository.removeEmitter(emitter);
            }
        }
    }

    private List<Long> getParticipantIdsForDiscussion(Long discussionId) {
        DiscussionEntity discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new IllegalArgumentException("Discussion not found: " + discussionId));

        return List.of(
                discussion.getParticipant1().getId(),
                discussion.getParticipant2().getId()
        );
    }
}
