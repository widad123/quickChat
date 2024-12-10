package com.springproject.quickchat.Entity;

import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_FriendRequest")
public class FriendRequestEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false) // Foreign key avec un nom explicite
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false) // Foreign key avec un nom explicite
    private UserEntity receiver;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public enum RequestStatus {
        PENDING, ACCEPTED, DECLINED
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }

}

