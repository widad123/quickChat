package com.springproject.quickchat.service;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import com.springproject.quickchat.model.FriendRequest;
import com.springproject.quickchat.repository.InMemoryFriendRequestRepository;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendRequestServiceTest {

    @Test
    void testSendAndRetrieveFriendRequests() {
        InMemoryFriendRequestRepository inMemoryRepository = new InMemoryFriendRequestRepository();
        FriendRequestService service = new FriendRequestService(inMemoryRepository);

        FriendRequest request1 = new FriendRequest();
        request1.setSenderId(1L);
        request1.setReceiverId(2L);

        FriendRequest request2 = new FriendRequest();
        request2.setSenderId(3L);
        request2.setReceiverId(4L);

        service.sendFriendRequest(request1);
        service.sendFriendRequest(request2);
        List<FriendRequestEntity> allRequests = service.getAllFriendRequests();

        assertEquals(2, allRequests.size());
        assertEquals(1L, allRequests.get(0).getSenderId());
        assertEquals(3L, allRequests.get(1).getSenderId());
    }
}