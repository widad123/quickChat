package com.springproject.quickchat.Entity;

import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEntity {
    @Id
    private Long id;

    private String action; // e.g., "Message Sent", "File Uploaded"
    private String details;

    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }
}

