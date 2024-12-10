package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.DiscussionEntity;
import com.springproject.quickchat.Entity.FileEntity;
import com.springproject.quickchat.Entity.MessageEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.model.Message;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryMessageRepository implements MessageRepositoryInterface {
    private final Map<Long, MessageEntity> messages = new HashMap<>();
    private final Map<Long, DiscussionEntity> discussions = new HashMap<>();
    private final Map<Long, UserEntity> users = new HashMap<>();

    public InMemoryMessageRepository() {
    }

    @Override
    public void save(Message message) {
        DiscussionEntity discussionEntity = Optional.ofNullable(discussions.get(message.getDiscussionId()))
                .orElseThrow(() -> new IllegalArgumentException("Discussion introuvable."));
        UserEntity senderEntity = Optional.ofNullable(users.get(message.getSender()))
                .orElseThrow(() -> new IllegalArgumentException("Émetteur introuvable."));
        UserEntity recipientEntity = Optional.ofNullable(users.get(message.getRecipient()))
                .orElseThrow(() -> new IllegalArgumentException("Recepteur introuvable."));
        FileEntity fileEntity = message.getAttachedFile() != null
                ? FileEntity.fromFile(message.getAttachedFile().snapshot())
                : null;

        MessageEntity messageEntity = MessageEntity.fromMessage(message.snapshot(), discussionEntity, senderEntity, recipientEntity, fileEntity);
        messages.put(messageEntity.getId(), messageEntity);
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        return Optional.ofNullable(messages.get(messageId))
                .map(MessageEntity::toSnapshot)
                .map(Message::fromSnapshot);
    }

    @Override
    public List<Message> findByDiscussionId(Long discussionId) {
        return messages.values().stream()
                .filter(entity -> entity.getDiscussion().getId().equals(discussionId))
                .map(MessageEntity::toSnapshot)
                .map(Message::fromSnapshot)
                .collect(Collectors.toList());
    }

    public void addDiscussion(DiscussionEntity discussionEntity) {
        discussions.put(discussionEntity.getId(), discussionEntity);
    }

    public void addDiscussion(Long id, Long user1Id, Long user2Id) {
        DiscussionEntity discussionEntity = new DiscussionEntity();
        discussionEntity.setId(id);

        UserEntity user1 = Optional.ofNullable(users.get(user1Id))
                .orElseThrow(() -> new IllegalArgumentException("User1 introuvable."));
        UserEntity user2 = Optional.ofNullable(users.get(user2Id))
                .orElseThrow(() -> new IllegalArgumentException("User2 introuvable."));

        discussionEntity.setParticipant1(user1);
        discussionEntity.setParticipant2(user2);

        discussions.put(id, discussionEntity);
    }


    public void addUser(UserEntity userEntity) {
        users.put(userEntity.getId(), userEntity);
    }

    public void addUser(Long id, String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUsername(name);
        addUser(userEntity);
    }


    public void clear() {
        messages.clear();
        discussions.clear();
        users.clear();
    }
}
