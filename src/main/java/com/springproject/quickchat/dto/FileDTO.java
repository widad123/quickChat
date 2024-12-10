package com.springproject.quickchat.dto;

import java.io.File;
import java.net.URL;

public class FileDTO {
    private final String fileName;
    private final String fileType;
    private final long fileSize;
    private final String fileUrl;

    public FileDTO(String fileName, String fileType, long fileSize, String fileUrl) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
    }

    public static FileDTO fromUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            File file = new File(url.getPath());

            if (!file.exists()) {
                throw new IllegalArgumentException("Le fichier n'existe pas à l'URL spécifiée.");
            }

            String fileName = file.getName();
            String fileType = getFileExtension(fileName);
            long fileSize = file.length();

            return new FileDTO(fileName, fileType, fileSize, fileUrl);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erreur lors de la récupération des informations du fichier : " + e.getMessage(), e);
        }
    }

    public static FileDTO fromPath(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("Le fichier n'existe pas au chemin spécifié.");
        }

        String fileName = file.getName();
        String fileType = getFileExtension(fileName);
        long fileSize = file.length();

        return new FileDTO(fileName, fileType, fileSize, file.getAbsolutePath());
    }

    private static String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1 || lastIndex == fileName.length() - 1) {
            throw new IllegalArgumentException("Impossible de déterminer le type de fichier.");
        }
        return fileName.substring(lastIndex + 1);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}

