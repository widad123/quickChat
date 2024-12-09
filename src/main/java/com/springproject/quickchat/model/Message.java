package com.springproject.quickchat.model;

import java.time.LocalDateTime;

public class Message {
    private final String id;
    private final String discussionId;
    private final String sender;
    private final String recipient;
    private MessageContent content;
    private final LocalDateTime timestamp;
    private boolean edited;
    private boolean deleted;

    private Message(String id, String discussionId, String sender, String recipient, MessageContent content, LocalDateTime timestamp) {
        this.id = id;
        this.discussionId = discussionId;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = timestamp;
        this.edited = false;
        this.deleted = false;
    }

    public void editContent(String newContent) {
        if (deleted) {
            throw new IllegalArgumentException("Impossible d'éditer un message supprimé.");
        }
        this.content.update(newContent);
        this.edited = true;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isEdited() {
        return edited;
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

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content.getValue();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public record Snapshot(String id, String discussionId, String sender, String recipient, String content, LocalDateTime timestamp, boolean edited, boolean deleted) {
    }

    public Snapshot snapshot() {
        return new Snapshot(id, discussionId, sender, recipient, content.getValue(), timestamp, edited, deleted);
    }

    public static Message fromSnapshot(Snapshot snapshot) {
        Message message = new Message(
                snapshot.id(),
                snapshot.discussionId(),
                snapshot.sender(),
                snapshot.recipient(),
                MessageContent.from(snapshot.content()),
                snapshot.timestamp()
        );
        message.edited = snapshot.edited();
        message.deleted = snapshot.deleted();
        return message;
    }

    public static Message create(String id, String discussionId, String sender, String recipient, String content, LocalDateTime timestamp) {
        return new Message(id, discussionId, sender, recipient, MessageContent.from(content), timestamp);
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
