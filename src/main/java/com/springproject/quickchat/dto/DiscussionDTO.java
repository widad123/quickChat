package com.springproject.quickchat.dto;

public class DiscussionDTO {

    private Long id;
    private Long user1Id;
    private Long user2Id;
    private String title;

    public DiscussionDTO() {
    }

    public DiscussionDTO(Long user1Id, Long user2Id, String title) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.title = title;
    }

    public DiscussionDTO(Long id, Long user1Id, Long user2Id, String title) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Long user1Id) {
        this.user1Id = user1Id;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Long user2Id) {
        this.user2Id = user2Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
