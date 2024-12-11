package com.springproject.quickchat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MessageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime timestamp;

    public MessageLog() {}

    public MessageLog(Long senderId, Long recipientId, String content, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.timestamp = timestamp;
    }

}

