package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, UserEntity> users = new ConcurrentHashMap<>();
    private long idSequence = 1;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = UserEntity.fromUser(user.snapshot());
        if (userEntity.getId() == null) {
            userEntity.setId(idSequence++);
        }
        users.put(userEntity.getId(), userEntity);
    }

    @Override
    public <S extends UserEntity> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(idSequence++);
        }
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }

    @Override
    public void delete(UserEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends UserEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<UserEntity> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<UserEntity> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UserEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<UserEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public UserEntity getById(Long aLong) {
        return null;
    }

    @Override
    public UserEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends UserEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UserEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<UserEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return null;
    }
}
