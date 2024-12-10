package com.springproject.quickchat.model;

import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private final Long discussionId;
    private final Long sender;
    private final Long recipient;
    private MessageContent content;
    private final LocalDateTime timestamp;
    private boolean edited;
    private boolean deleted;
    private File attachedFile;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }

    private Message(Long id, Long discussionId, Long sender, Long recipient, MessageContent content, LocalDateTime timestamp, File attachedFile) {
        this.id = id;
        this.discussionId = discussionId;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = timestamp;
        this.attachedFile = attachedFile;
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

    public Long getId() {
        return id;
    }

    public Long getDiscussionId() {
        return discussionId;
    }

    public Long getSender() {
        return sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content.getValue();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public File getAttachedFile() {
        return attachedFile;
    }

    public record Snapshot(Long id, Long discussionId, Long sender, Long recipient, String content, LocalDateTime timestamp, boolean edited, boolean deleted, File.Snapshot attachedFile) {}

    public Snapshot snapshot() {
        return new Snapshot(
                id,
                discussionId,
                sender,
                recipient,
                content.getValue(),
                timestamp,
                edited,
                deleted,
                attachedFile != null ? attachedFile.snapshot() : null
        );
    }

    public static Message fromSnapshot(Snapshot snapshot) {
        File attachedFile = snapshot.attachedFile() != null ? File.fromSnapshot(snapshot.attachedFile()) : null;
        Message message = new Message(
                snapshot.id(),
                snapshot.discussionId(),
                snapshot.sender(),
                snapshot.recipient(),
                MessageContent.from(snapshot.content()),
                snapshot.timestamp(),
                attachedFile
        );
        message.edited = snapshot.edited();
        message.deleted = snapshot.deleted();
        return message;
    }

    public static Message create(Long id, Long discussionId, Long sender, Long recipient, String content, LocalDateTime timestamp, File attachedFile) {
        return new Message(id, discussionId, sender, recipient, MessageContent.from(content), timestamp, attachedFile);
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
