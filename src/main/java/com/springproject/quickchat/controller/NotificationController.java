package com.springproject.quickchat.controller;

import com.springproject.quickchat.repository.SseEmitterRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final SseEmitterRepository sseEmitterRepository;

    public NotificationController(SseEmitterRepository sseEmitterRepository) {
        this.sseEmitterRepository = sseEmitterRepository;
    }

    @GetMapping("/subscribe/{userId}")
    public SseEmitter subscribe(@PathVariable Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitterRepository.addEmitter(userId, emitter);

        emitter.onCompletion(() -> sseEmitterRepository.removeEmitter(userId));
        emitter.onTimeout(() -> sseEmitterRepository.removeEmitter(userId));

        return emitter;
    }
}