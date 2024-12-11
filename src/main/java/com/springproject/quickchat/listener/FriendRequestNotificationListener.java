package com.springproject.quickchat.listener;

import com.springproject.quickchat.events.FriendRequestReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestNotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(FriendRequestNotificationListener.class);

    @EventListener
    public void handleFriendRequestReceived(FriendRequestReceivedEvent event) {
        // Récupérer les détails de l'invitation
        Long senderId = event.getFriendRequest().getSenderId();
        Long receiverId = event.getFriendRequest().getReceiverId();

        // Afficher une notification (ou autre logique métier)
        logger.info("Notification: User {} received a friend request from User {}.", receiverId, senderId);
    }
}
