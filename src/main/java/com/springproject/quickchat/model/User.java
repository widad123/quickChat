package com.springproject.quickchat.model;


import java.util.List;

public class User {
    private Long id;
    private final String username;
    private final String password;
    private final String email;
    private final List<Long> sentRequestIds;
    private final List<Long> receivedRequestIds;
    private final List<Long> friendIds;
    private final Snapshot snapshot;

    private User(Long id, String username, String password, String email, List<Long> sentRequestIds, List<Long> receivedRequestIds, List<Long> friendIds, Snapshot snapshot) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.sentRequestIds = sentRequestIds;
        this.receivedRequestIds = receivedRequestIds;
        this.friendIds = friendIds;
        this.snapshot = snapshot;
    }

    public static User create(Long id, String username, String password, String email, List<Long> sentRequestIds, List<Long> receivedRequestIds, List<Long> friendIds) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return new User(id, username, password, email, sentRequestIds, receivedRequestIds, friendIds, null);
    }

    public static User fromSnapshot(Snapshot snapshot) {
        return new User(snapshot.id, snapshot.username, snapshot.password, snapshot.email, snapshot.sentRequestIds, snapshot.receivedRequestIds, snapshot.friendIds, snapshot);
    }

    public Snapshot snapshot() {
        return new Snapshot(id, username, password, email, sentRequestIds, receivedRequestIds, friendIds);
    }

    public record Snapshot(Long id, String username, String password, String email, List<Long> sentRequestIds, List<Long> receivedRequestIds, List<Long> friendIds) {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getSentRequestIds() {
        return sentRequestIds;
    }

    public List<Long> getReceivedRequestIds() {
        return receivedRequestIds;
    }

    public List<Long> getFriendIds() {
        return friendIds;
    }
}
