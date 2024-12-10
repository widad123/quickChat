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
import java.util.function.Function;

public class InMemoryFriendRequestRepository implements FriendRequestRepository {

    private final List<FriendRequestEntity> requests = new ArrayList<>();
    private Long idCounter = 1L;

    @Override
    public FriendRequestEntity save(FriendRequestEntity entity) {
        entity.setId(idCounter++);
        requests.add(entity);
        return entity;
    }

    @Override
    public Optional<FriendRequestEntity> findById(Long id) {
        return requests.stream().filter(req -> req.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public <S extends FriendRequestEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<FriendRequestEntity> findAll() {
        return new ArrayList<>(requests);
    }

    @Override
    public List<FriendRequestEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        requests.removeIf(req -> req.getId().equals(id));
    }

    @Override
    public void delete(FriendRequestEntity entity) {
        requests.remove(entity);
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
    public long count() {
        return requests.size();
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
        return null;
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
        return null;
    }

    @Override
    public <S extends FriendRequestEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
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
        return null;
    }

    @Override
    public Page<FriendRequestEntity> findAll(Pageable pageable) {
        return null;
    }

    // Default method saveFriendRequest is inherited from the repository
}