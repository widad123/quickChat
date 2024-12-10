package com.springproject.quickchat.model;

import java.time.LocalDateTime;

public class FriendRequest {
    private final String from;
    private final String to;
    private final LocalDateTime createdAt;
    private boolean accepted;
    private boolean declined;

    public FriendRequest(String from, String to, LocalDateTime createdAt) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same user.");
        }
        this.from = from;
        this.to = to;
        this.createdAt = createdAt;
        this.accepted = false;
        this.declined = false;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isDeclined() {
        return declined;
    }

    public void accept() {
        if (declined) {
            throw new IllegalStateException("Cannot accept a declined request.");
        }
        this.accepted = true;
    }

    public void decline() {
        if (accepted) {
            throw new IllegalStateException("Cannot decline an accepted request.");
        }
        this.declined = true;
    }

    public Snapshot snapshot() {
        return new Snapshot(from, to, createdAt, accepted, declined);
    }

    public static FriendRequest fromSnapshot(Snapshot snapshot) {
        FriendRequest request = new FriendRequest(snapshot.from(), snapshot.to(), snapshot.createdAt());
        if (snapshot.accepted()) {
            request.accept();
        }
        if (snapshot.declined()) {
            request.decline();
        }
        return request;
    }

    public record Snapshot(String from, String to, LocalDateTime createdAt, boolean accepted, boolean declined) {}
}