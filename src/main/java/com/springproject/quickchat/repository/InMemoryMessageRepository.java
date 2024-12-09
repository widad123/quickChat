package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.MessageEntity;
import com.springproject.quickchat.model.Message;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class InMemoryMessageRepository implements MessageRepository {

    private final List<MessageEntity> messages = new ArrayList<>();

    @Override
    public <S extends MessageEntity> S save(S entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("L'ID de l'entité ne peut pas être null.");
        }

        boolean removed = messages.removeIf(existing -> existing.getId().equals(entity.getId()));
        System.out.println("Entity removed: " + removed);

        messages.add(entity);

        System.out.println("Saved message: " + entity + ", deleted = " + entity.isDeleted());

        return entity;
    }


    @Override
    public void save(Message message) {
        Optional<MessageEntity> existingEntity = findById(message.getId());
        existingEntity.ifPresent(messages::remove);

        MessageEntity entity = MessageEntity.fromMessage(message.snapshot());

        messages.add(entity);

        System.out.println("Saved message: " + entity + ", deleted = " + entity.isDeleted());
    }



    @Override
    public Optional<MessageEntity> findById(String id) {
        return messages.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }


    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Optional<Message> findMessageById(String id) {
        return messages.stream()
                .filter(entity -> entity.getId().equals(id))
                .peek(entity -> System.out.println("Found entity: " + entity + ", deleted = " + entity.isDeleted()))
                .map(entity -> {
                    Message message = Message.fromSnapshot(entity.toSnapshot());
                    System.out.println("Mapped to message: " + message + ", deleted = " + message.isDeleted());
                    return message;
                })
                .findFirst();
    }



    @Override
    public List<MessageEntity> findAllByDiscussionId(String discussionId) {
        return messages.stream()
                .filter(entity -> entity.getDiscussionId().equals(discussionId))
                .collect(Collectors.toList());
    }

    public List<Message> findMessagesByDiscussionId(String discussionId) {
        return findAllByDiscussionId(discussionId).stream()
                .map(entity -> Message.fromSnapshot(entity.toSnapshot()))
                .collect(Collectors.toList());
    }


    @Override
    public <S extends MessageEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<MessageEntity> findAll() {
        return new ArrayList<>(messages);
    }

    @Override
    public List<MessageEntity> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public void deleteById(String id) {
        messages.removeIf(entity -> entity.getId().equals(id));
    }

    @Override
    public void delete(MessageEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends MessageEntity> entities) {

    }

    @Override
    public long count() {
        return messages.size();
    }

    @Override
    public void deleteAll() {
        messages.clear();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends MessageEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends MessageEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<MessageEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MessageEntity getOne(String s) {
        return null;
    }

    @Override
    public MessageEntity getById(String s) {
        return null;
    }

    @Override
    public MessageEntity getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends MessageEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MessageEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends MessageEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends MessageEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MessageEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MessageEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends MessageEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<MessageEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<MessageEntity> findAll(Pageable pageable) {
        return null;
    }
}
