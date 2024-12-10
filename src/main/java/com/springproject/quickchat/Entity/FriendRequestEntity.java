package com.springproject.quickchat.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_FriendRequest")
public class FriendRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String from;
    private String to;
    private boolean accepted;
    private boolean declined;

    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public FriendRequestEntity(String from, String to) {
        this.from = from;
        this.to = to;
        this.accepted = false;
        this.declined = false;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isDeclined() {
        return declined;
    }

    public void accept() {
        this.accepted = true;
    }

    public void decline() {
        this.declined = true;
    }
}

