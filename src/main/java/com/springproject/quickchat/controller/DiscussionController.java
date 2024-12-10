/*package com.springproject.quickchat.controller;

import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @PostMapping
    public void createDiscussion(@RequestParam String user1, @RequestParam String user2) {
        discussionService.createDiscussion(user1, user2);
    }

    @GetMapping
    public List<Discussion> getDiscussionsForUser(@RequestParam String userId) {
        return discussionService.getDiscussionsForUser(userId);
    }
}
*/