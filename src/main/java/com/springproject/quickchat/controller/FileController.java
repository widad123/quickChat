package com.springproject.quickchat.controller;

import com.springproject.quickchat.model.File;
import com.springproject.quickchat.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public void uploadFile(
            @RequestParam String id,
            @RequestParam String discussionId,
            @RequestParam String sender,
            @RequestParam String filename,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        long fileSizeInBytes = file.getSize();

        String url = "/uploads/" + file.getOriginalFilename();

        File uploadedFile = new File(id, discussionId, sender, filename, url, LocalDateTime.now().toString());
        fileService.uploadFile(uploadedFile, fileSizeInBytes);

        //file.transferTo(new java.io.File("uploads/" + file.getOriginalFilename()));
    }


    @DeleteMapping("/delete")
    public void deleteFile(@RequestParam String fileId) {
        fileService.deleteFile(fileId);
    }
}
