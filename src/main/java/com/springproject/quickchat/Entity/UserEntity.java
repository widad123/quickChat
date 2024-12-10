package com.springproject.quickchat.Entity;

import com.springproject.quickchat.model.User;
import com.springproject.quickchat.utils.UuidToLongGenerator;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequestEntity> sentRequests;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequestEntity> receivedRequests;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<UserEntity> friends;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new UuidToLongGenerator().generateId();
        }
    }
    public static UserEntity fromUser(User.Snapshot snapshot) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(snapshot.id());
        userEntity.setUsername(snapshot.username());
        userEntity.setPassword(snapshot.password());
        userEntity.setEmail(snapshot.email());
        userEntity.setReceivedRequests(new ArrayList<>());
        userEntity.setSentRequests(new ArrayList<>());
        userEntity.setFriends(new ArrayList<UserEntity>());
        return userEntity;
    }

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserEntity(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<FriendRequestEntity> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(List<FriendRequestEntity> sentRequests) {
        this.sentRequests = sentRequests;
    }

    public List<FriendRequestEntity> getReceivedRequests() {
        return receivedRequests;
    }

    public void setReceivedRequests(List<FriendRequestEntity> receivedRequests) {
        this.receivedRequests = receivedRequests;
    }

    public List<UserEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<UserEntity> friends) {
        this.friends = friends;
    }
}
