package com.springproject.quickchat.events;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import org.springframework.context.ApplicationEvent;

public class FriendRequestReceivedEvent extends ApplicationEvent {

    private final FriendRequestEntity friendRequest;

    public FriendRequestReceivedEvent(Object source, FriendRequestEntity friendRequest) {
        super(source);
        this.friendRequest = friendRequest;
    }

    public FriendRequestEntity getFriendRequest() {
        return friendRequest;
    }
}