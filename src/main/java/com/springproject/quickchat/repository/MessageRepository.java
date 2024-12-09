package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.MessageEntity;
import com.springproject.quickchat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {

    public default Optional<Message> findMessageById(String messageId) {
        return findById(messageId)
                .map(entity -> Message.fromSnapshot(entity.toSnapshot()));
    }

    List<MessageEntity> findAllByDiscussionId(String discussionId);

    default void save(Message message) {

        MessageEntity entity = MessageEntity.fromMessage(message.snapshot());
        save(entity);
    }
    default List<Message> findMessagesByDiscussionId(String discussionId) {
        return findAllByDiscussionId(discussionId).stream()
                .map(entity -> Message.fromSnapshot(entity.toSnapshot()))
                .collect(Collectors.toList());
    }
    Optional<MessageEntity> findById(String messageId);
}
