package com.springproject.quickchat.model;

import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.PrePersist;

import java.util.List;

public class File {
    private Long id;
    private final String name;
    private final String type;
    private final long size; // en octets
    private final String url;
    private boolean isDeleted;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }

    private File(Long id, String name, String type, long size, String url, boolean isDeleted) {
        validateFile(type, size);
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.url = url;
        this.isDeleted = isDeleted;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    private void validateFile(String type, long size) {
        List<String> allowedTypes = List.of("image/png", "image/jpeg", "video/mp4");
        long maxFileSize = 200 * 1024 * 1024;

        if (!allowedTypes.contains(type)) {
            throw new IllegalArgumentException("Type de fichier non pris en charge : " + type);
        }

        if (size > maxFileSize) {
            throw new IllegalArgumentException("Le fichier dépasse la taille maximale autorisée de 200 Mo.");
        }
    }

    public static File create(Long id, String name, String type, long size, String url, boolean isDeleted) {
        return new File(id, name, type, size, url, isDeleted);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public boolean isDeleted() {return isDeleted;}

    public record Snapshot(Long id, String name, String type, long size, String url, boolean isDeleted) {}

    public Snapshot snapshot() {
        return new Snapshot(id, name, type, size, url, isDeleted);
    }

    public static File fromSnapshot(Snapshot snapshot) {
        return new File(snapshot.id(), snapshot.name(), snapshot.type(), snapshot.size(), snapshot.url(), snapshot.isDeleted());
    }
}