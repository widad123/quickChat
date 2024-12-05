package com.springproject.quickchat.model;

public class Discussion {
    private String id;
    private String user1;
    private String user2;

    public Discussion(String id, String user1, String user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getId() {
        return id;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }
}

