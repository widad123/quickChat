package com.springproject.quickchat.model;

public class FriendRequest {
    private String from;
    private String to;
    private boolean accepted;
    private boolean declined;

    public FriendRequest(String from, String to) {
        this.from = from;
        this.to = to;
        this.accepted = false;
        this.declined = false;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
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

