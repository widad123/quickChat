package com.springproject.quickchat.controller;

import com.springproject.quickchat.dto.MessageRequest;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestBody MessageRequest messageRequest
    ) {
        messageService.sendMessage(messageRequest.getMessage(), messageRequest.getFile());
        return ResponseEntity.ok("Message envoyé avec succès.");
    }


    @PutMapping("/edit")
    public Message editMessage(
            @RequestParam Long messageId,
            @RequestParam String newContent
    ) {
        return messageService.editMessage(messageId, newContent);
    }

    @DeleteMapping("/deleteFile/{messageId}")
    public ResponseEntity<String> deleteFileFromMessage(@PathVariable Long messageId) {
        messageService.removeFileFromMessage(messageId);
        return ResponseEntity.ok("File removed from message successfully");
    }


    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(
            @PathVariable Long messageId
    ) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.ok("Message deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }


}