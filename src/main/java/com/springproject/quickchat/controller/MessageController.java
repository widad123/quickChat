package com.springproject.quickchat.controller;

import com.springproject.quickchat.model.MessageDTO;
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
    public MessageDTO sendMessage(@RequestParam String sender, @RequestParam String recipient, @RequestParam String content) {
        return messageService.sendMessage(sender, recipient, content);
    }

    @GetMapping("/received")
    public List<MessageDTO> getMessagesForRecipient(@RequestParam String recipient) {
        return messageService.getMessagesForRecipient(recipient);
    }
}