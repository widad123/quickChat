package com.springproject.quickchat.service;

import com.springproject.quickchat.model.File;
import com.springproject.quickchat.repository.DiscussionRepository;
import com.springproject.quickchat.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final DiscussionRepository discussionRepository;
    private final List<String> allowedFileExtensions = Arrays.asList("png", "jpg", "mp4");
    private final long maxFileSizeInBytes = 200 * 1024 * 1024;

    public FileService(FileRepository fileRepository, DiscussionRepository discussionRepository) {
        this.fileRepository = fileRepository;
        this.discussionRepository = discussionRepository;
    }

    public void uploadFile(File file, long fileSizeInBytes) {

        String fileExtension = getFileExtension(file.getFilename());
        if (!allowedFileExtensions.contains(fileExtension.toLowerCase())) {
            throw new IllegalArgumentException("Type de fichier non autorisé.");
        }

        if (fileSizeInBytes > maxFileSizeInBytes) {
            throw new IllegalArgumentException("La taille du fichier dépasse la limite de 200 Mo.");
        }

        if (discussionRepository.findById(file.getDiscussionId()) == null) {
            throw new IllegalArgumentException("La discussion spécifiée n'existe pas.");
        }

        fileRepository.save(file);
    }

    public void deleteFile(String fileId) {
        File file = fileRepository.findById(fileId);
        if (file == null || file.isDeleted()) {
            throw new IllegalArgumentException("Fichier non trouvé ou déjà supprimé.");
        }
        fileRepository.delete(file);
    }

    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf('.');
        if (lastIndex == -1 || lastIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastIndex + 1);
    }
}
