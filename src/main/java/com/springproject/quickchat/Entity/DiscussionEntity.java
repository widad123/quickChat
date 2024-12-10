package com.springproject.quickchat.Entity;

import com.springproject.quickchat.model.Discussion;
import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;


import java.util.List;


@Entity
@Table(name = "_Discussion")
public class DiscussionEntity {

    @Id
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "participant1_id", nullable = false)
    private UserEntity participant1;

    @ManyToOne
    @JoinColumn(name = "participant2_id", nullable = false)
    private UserEntity participant2;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageEntity> messages;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserEntity getParticipant1() {
        return participant1;
    }

    public void setParticipant1(UserEntity participant1) {
        this.participant1 = participant1;
    }

    public UserEntity getParticipant2() {
        return participant2;
    }

    public void setParticipant2(UserEntity participant2) {
        this.participant2 = participant2;
    }
}