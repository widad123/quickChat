package com.springproject.quickchat.model;

public class File {
    private String id;
    private String discussionId;
    private String sender;
    private String filename;
    private String url;
    private String timestamp;
    private boolean deleted;

    public File(String id, String discussionId, String sender, String filename, String url, String timestamp) {
        this.id = id;
        this.discussionId = discussionId;
        this.sender = sender;
        this.filename = filename;
        this.url = url;
        this.timestamp = timestamp;
        this.deleted = false;
    }

    public String getId() {
        return id;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public String getSender() {
        return sender;
    }

    public String getFilename() {
        return filename;
    }

    public String getUrl() {
        return url;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }
}
