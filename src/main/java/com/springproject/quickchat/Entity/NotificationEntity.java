package com.springproject.quickchat.Entity;

import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class NotificationEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false) // Foreign key avec un nom explicite
    private UserEntity recipient;

    private String message; // e.g., "You received a friend request from X."

    private boolean seen;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }
}
