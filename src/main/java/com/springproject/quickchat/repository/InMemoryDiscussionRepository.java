package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.DiscussionEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Repository
public class InMemoryDiscussionRepository implements DiscussionRepository {

    private final Map<Long, DiscussionEntity> discussions = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<DiscussionEntity> findById(Long id) {
        return Optional.ofNullable(discussions.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public <S extends DiscussionEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<DiscussionEntity> findAll() {
        return new ArrayList<>(discussions.values());
    }

    @Override
    public List<DiscussionEntity> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public DiscussionEntity save(DiscussionEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }
        discussions.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        discussions.remove(id);
    }

    @Override
    public void delete(DiscussionEntity entity) {
        discussions.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends DiscussionEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<DiscussionEntity> findDiscussionByUsers(Long user1Id, Long user2Id) {
        return discussions.values().stream()
                .filter(d -> (d.getParticipant1().getId().equals(user1Id) && d.getParticipant2().getId().equals(user2Id)) ||
                        (d.getParticipant1().getId().equals(user2Id) && d.getParticipant2().getId().equals(user1Id)))
                .findFirst();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends DiscussionEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends DiscussionEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<DiscussionEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public DiscussionEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public DiscussionEntity getById(Long aLong) {
        return null;
    }

    @Override
    public DiscussionEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends DiscussionEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DiscussionEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends DiscussionEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends DiscussionEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DiscussionEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DiscussionEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DiscussionEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<DiscussionEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<DiscussionEntity> findAll(Pageable pageable) {
        return null;
    }
}