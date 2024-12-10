/*package com.springproject.quickchat.service;

import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.model.File;
import com.springproject.quickchat.repository.InMemoryDiscussionRepository;
import com.springproject.quickchat.repository.InMemoryFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    private FileService fileService;
    private InMemoryFileRepository fileRepository;
    private InMemoryDiscussionRepository discussionRepository;

    @BeforeEach
    void setUp() {
        fileRepository = new InMemoryFileRepository();
        discussionRepository = new InMemoryDiscussionRepository();
        fileService = new FileService(fileRepository, discussionRepository);
    }

    @Test
    void uploadFile_validFile_savesFile() {
        discussionRepository.save(new Discussion("1", "Alice", "Bob"));
        File file = new File("1", "1", "Alice", "image.png", "http://example.com/image.png", LocalDateTime.now().toString());

        fileService.uploadFile(file, 150 * 1024 * 1024); // 150 Mo

        List<File> files = fileRepository.findAllByDiscussionId("1");
        assertTrue(files.contains(file), "Le fichier n'a pas été correctement ajouté.");
        assertEquals(1, files.size(), "Le nombre de fichiers dans la discussion est incorrect.");
    }

    @Test
    void uploadFile_invalidFileType_throwsException() {
        discussionRepository.save(new Discussion("1", "Alice", "Bob"));
        File file = new File("1", "1", "Alice", "document.pdf", "http://example.com/document.pdf", LocalDateTime.now().toString());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> fileService.uploadFile(file, 100 * 1024 * 1024));
        assertEquals("Type de fichier non autorisé.", exception.getMessage());
    }


    @Test
    void uploadFile_exceedsMaxSize_throwsException() {
        discussionRepository.save(new Discussion("1", "Alice", "Bob"));
        File file = new File("1", "1", "Alice", "video.mp4", "http://example.com/video.mp4",LocalDateTime.now().toString());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> fileService.uploadFile(file, 201 * 1024 * 1024));
        assertEquals("La taille du fichier dépasse la limite de 200 Mo.", exception.getMessage());
    }


    @Test
    void deleteFile_validFile_marksAsDeleted() {
        discussionRepository.save(new Discussion("1", "Alice", "Bob"));
        File file = new File("1", "1", "Alice", "image.png", "http://example.com/image.png", LocalDateTime.now().toString());
        fileRepository.save(file);

        fileService.deleteFile("1");

        assertTrue(file.isDeleted(), "Le fichier n'a pas été marqué comme supprimé.");
    }

    @Test
    void deleteFile_nonexistentFile_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> fileService.deleteFile("nonexistent"));
        assertEquals("Fichier non trouvé ou déjà supprimé.", exception.getMessage());
    }
}
*/