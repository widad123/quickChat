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
    public void sendMessage(
            @RequestBody MessageRequest messageRequest
    ) {
        messageService.sendMessage(messageRequest.getSenderId(), messageRequest.getMessage(), messageRequest.getFile());
    }


    @PutMapping("/edit")
    public Message editMessage(
            @RequestParam Long messageId,
            @RequestParam String newContent
    ) {
        return messageService.editMessage(messageId, newContent);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteFileFromMessage(@PathVariable Long messageId) {
        messageService.removeFileFromMessage(messageId);
        return ResponseEntity.ok("File removed from message successfully");
    }


}