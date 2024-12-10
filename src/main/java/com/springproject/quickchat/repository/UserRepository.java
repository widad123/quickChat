package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(Long id);

    default void saveUser(User user) {
        UserEntity savedEntity = UserEntity.fromUser(user.snapshot());
        this.save(savedEntity);
    }
}
