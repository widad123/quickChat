package com.springproject.quickchat.Entity;

import com.springproject.quickchat.model.Message;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "_Message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String discussionId;
    private String sender;
    private String recipient;
    private String content;
    private LocalDateTime timestamp;
    private boolean edited;
    private boolean deleted;

    public MessageEntity() {}

    public MessageEntity(String id, String discussionId, String sender, String recipient, String content, LocalDateTime timestamp, boolean edited, boolean deleted) {
        this.id = id;
        this.discussionId = discussionId;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = timestamp;
        this.edited = edited;
        this.deleted = deleted;
    }

    public static MessageEntity fromMessage(Message.Snapshot snapshot) {
        return new MessageEntity(
                snapshot.id(),
                snapshot.discussionId(),
                snapshot.sender(),
                snapshot.recipient(),
                snapshot.content(),
                snapshot.timestamp(),
                snapshot.edited(),
                snapshot.deleted()
        );
    }

    public Message.Snapshot toSnapshot() {
        return new Message.Snapshot(
                id,
                discussionId,
                sender,
                recipient,
                content,
                timestamp,
                edited,
                deleted
        );
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
