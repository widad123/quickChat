package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.events.FriendRequestReceivedEvent;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.InMemoryFriendRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.GenericApplicationContext;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FriendRequestEventTest {

    @Test
    void testFriendRequestEvent() {
        // Mock Repository
        InMemoryFriendRequestRepository repository = new InMemoryFriendRequestRepository();

        // Mock Event Publisher
        GenericApplicationContext context = new GenericApplicationContext();
        ApplicationEventPublisher publisher = context;

        FriendRequestService service = new FriendRequestService(repository, publisher);

        // Listener pour capturer l'événement
        context.registerBean(FriendRequestNotificationListener.class, () -> new FriendRequestNotificationListener());
        context.refresh();

        // Simuler une invitation
        FriendRequest request = new FriendRequest();
        request.setSenderId(1L);
        request.setReceiverId(2L);

        FriendRequestEntity savedRequest = service.sendFriendRequest(request);

        // Vérifier que l'invitation est bien sauvegardée
        assertEquals(1, repository.findAll().size());
        assertEquals(1L, savedRequest.getSenderId());
        assertEquals(2L, savedRequest.getReceiverId());
    }

    // Listener pour tester les notifications
    static class FriendRequestNotificationListener {

        @EventListener
        public void handleFriendRequestReceived(FriendRequestReceivedEvent event) {
            System.out.println("Test Notification: User " + event.getFriendRequest().getReceiverId() +
                    " received a friend request from User " + event.getFriendRequest().getSenderId());
        }
    }
}