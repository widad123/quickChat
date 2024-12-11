package com.springproject.quickchat.dto;

public class FriendRequestDto {

    private Long receiverId;

    public FriendRequestDto() {
    }

    public FriendRequestDto(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
