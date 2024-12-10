package com.springproject.quickchat.model;

import com.springproject.quickchat.Entity.DiscussionEntity;
import com.springproject.quickchat.Entity.UserEntity;


public class Discussion {
    private Long id;
    private Long user1Id;
    private Long user2Id;

    public Discussion(Long id, Long user1Id, Long user2Id) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public static Discussion create(Long user1Id, Long user2Id) {
        if (user1Id == null || user2Id == null) {
            throw new IllegalArgumentException("Les participants ne peuvent pas être nuls.");
        }
        if (user1Id.equals(user2Id)) {
            throw new IllegalArgumentException("Une discussion ne peut pas être créée avec un seul participant.");
        }
        return new Discussion(null, user1Id, user2Id);
    }

    public Long getId() {
        return id;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public DiscussionEntity toEntity(UserEntity user1, UserEntity user2) {
        DiscussionEntity entity = new DiscussionEntity();
        entity.setId(this.id);
        entity.setParticipant1(user1);
        entity.setParticipant2(user2);
        return entity;
    }
}


