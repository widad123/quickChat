package com.springproject.quickchat.controller;

import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        try {
            messageService.sendMessage(message);
            return ResponseEntity.ok("Message envoyé avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{discussionId}")
    public List<Message> getMessagesForDiscussion(@PathVariable String discussionId) {
        return messageService.getMessagesForDiscussion(discussionId);
    }

}