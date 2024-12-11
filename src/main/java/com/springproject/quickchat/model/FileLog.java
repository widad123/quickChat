package com.springproject.quickchat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FileLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long recipientId;
    private String fileName;
    private LocalDateTime timestamp;

    public FileLog() {}

    public FileLog(Long senderId, Long recipientId, String fileName, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.fileName = fileName;
        this.timestamp = timestamp;
    }

}
