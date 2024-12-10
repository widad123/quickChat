package com.springproject.quickchat.Entity;

import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
public class LogEntity {
    @Id
    private Long id;

    private String action;
    private String details;

    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }
}

