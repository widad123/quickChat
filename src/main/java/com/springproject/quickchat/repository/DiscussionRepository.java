package com.springproject.quickchat.repository;



import com.springproject.quickchat.Entity.DiscussionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DiscussionRepository extends JpaRepository<DiscussionEntity, Long> {

    @Query("SELECT d FROM DiscussionEntity d WHERE (d.participant1.id = :user1Id AND d.participant2.id = :user2Id) " +
            "OR (d.participant1.id = :user2Id AND d.participant2.id = :user1Id)")
    Optional<DiscussionEntity> findDiscussionByUsers(Long user1Id, Long user2Id);

    @Query("SELECT d FROM DiscussionEntity d WHERE d.participant1.id = :userId OR d.participant2.id = :userId")
    List<DiscussionEntity> findDiscussionsByUserId(@Param("userId") Long userId);
}
