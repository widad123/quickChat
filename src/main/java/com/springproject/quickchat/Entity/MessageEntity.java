package com.springproject.quickchat.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_Message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String discussionId;
    private String sender;
    private String content;
    private String timestamp;
    private boolean edited;
    private boolean deleted;

    public MessageEntity(String id, String discussionId, String sender, String content, String timestamp) {
        this.id = id;
        this.discussionId = discussionId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.edited = false;
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

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isEdited() {
        return edited;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setContent(String content) {
        this.content = content;
        this.edited = true;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }
}
