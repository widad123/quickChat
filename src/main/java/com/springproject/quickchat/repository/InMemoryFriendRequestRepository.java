package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.FriendRequestEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class InMemoryFriendRequestRepository implements FriendRequestRepository {

    private final Map<Long, FriendRequestEntity> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public FriendRequestEntity save(FriendRequestEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends FriendRequestEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<FriendRequestEntity> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<FriendRequestEntity> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    // Other JpaRepository methods can be implemented similarly if needed.

    @Override
    public void delete(FriendRequestEntity entity) {
        storage.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends FriendRequestEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public List<FriendRequestEntity> findAllById(Iterable<Long> ids) {
        return StreamSupport.stream(ids.spliterator(), false)
                .map(storage::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends FriendRequestEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends FriendRequestEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<FriendRequestEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public FriendRequestEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public FriendRequestEntity getById(Long aLong) {
        return null;
    }

    @Override
    public FriendRequestEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends FriendRequestEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends FriendRequestEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends FriendRequestEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends FriendRequestEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FriendRequestEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends FriendRequestEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends FriendRequestEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<FriendRequestEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<FriendRequestEntity> findAll(Pageable pageable) {
        return null;
    }
}
