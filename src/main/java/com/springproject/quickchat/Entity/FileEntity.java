package com.springproject.quickchat.Entity;

import com.springproject.quickchat.model.File;
import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {
    @Id
    private Long id;

    private String fileName;
    private String fileType;
    private long fileSize;
    private String fileUrl;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }

    public static FileEntity fromFile(File.Snapshot snapshot) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.id = snapshot.id();
        fileEntity.fileName = snapshot.name();
        fileEntity.fileType = snapshot.type();
        fileEntity.fileUrl = snapshot.url();
        return fileEntity;
    }

    public File.Snapshot toSnapshot() {
        return new File.Snapshot(
                this.id,
                this.fileName,
                this.fileType,
                this.fileSize,
                this.fileUrl
        );
    }
}