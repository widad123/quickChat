package com.springproject.quickchat.Entity;

import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "_Message")
public class MessageEntity {
    @Id
    private Long id;

    private String content;
    private LocalDateTime timestamp;
    private boolean edited;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private DiscussionEntity discussion;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private FileEntity file;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }

    public static MessageEntity fromMessage(Message.Snapshot snapshot, DiscussionEntity discussion, UserEntity sender, FileEntity file) {
        MessageEntity entity = new MessageEntity();
        entity.setId(snapshot.id());
        entity.setContent(snapshot.content());
        entity.setTimestamp(snapshot.timestamp());
        entity.setEdited(snapshot.edited());
        entity.setDeleted(snapshot.deleted());
        entity.setDiscussion(discussion);
        entity.setSender(sender);
        entity.setFile(file);
        return entity;
    }

    public Message.Snapshot toSnapshot() {
        return new Message.Snapshot(
                this.id,
                this.discussion.getId(),
                this.sender.getId(),
                null,
                this.content,
                this.timestamp,
                this.edited,
                this.deleted,
                this.file != null ? this.file.toSnapshot() : null
        );
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public DiscussionEntity getDiscussion() {
        return discussion;
    }

    public void setDiscussion(DiscussionEntity discussion) {
        this.discussion = discussion;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }
}
