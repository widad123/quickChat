package com.springproject.quickchat.controller;

import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Message sendMessage(@RequestParam String discussionId, @RequestParam String sender, @RequestParam String content) {
        return messageService.sendMessage(discussionId, sender, content);
    }

    @GetMapping("/received")
    public List<Message> getMessagesForDiscussion(@RequestParam String discussionId) {
        return messageService.getMessagesForDiscussion(discussionId);
    }
}