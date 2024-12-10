package com.springproject.quickchat.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_File")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String discussionId;
    private String sender;
    private String filename;
    private String url;
    private String timestamp;
    private boolean deleted;

    public FileEntity(Long id, String discussionId, String sender, String filename, String url, String timestamp) {
        this.id = id;
        this.discussionId = discussionId;
        this.sender = sender;
        this.filename = filename;
        this.url = url;
        this.timestamp = timestamp;
        this.deleted = false;
    }
    public boolean isDeleted() {
        return deleted;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }
}
