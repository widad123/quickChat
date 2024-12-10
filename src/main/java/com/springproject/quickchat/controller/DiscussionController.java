package com.springproject.quickchat.controller;

import com.springproject.quickchat.dto.DiscussionDTO;
import com.springproject.quickchat.service.DiscussionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

    private final DiscussionService discussionService;

    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DiscussionDTO>> getUserDiscussions(@PathVariable Long userId) {
        List<DiscussionDTO> discussions = discussionService.getUserDiscussions(userId);
        return ResponseEntity.ok(discussions);
    }
}