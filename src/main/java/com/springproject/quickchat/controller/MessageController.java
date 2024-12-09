package com.springproject.quickchat.controller;

import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.exception.MessageNotFoundException;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(@RequestParam String senderId,@RequestBody MessageDTO message) {
         messageService.sendMessage(senderId, message);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleMessageNotFound(MessageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}