package com.springproject.quickchat.repository;

import com.springproject.quickchat.Entity.DiscussionEntity;
import com.springproject.quickchat.Entity.FileEntity;
import com.springproject.quickchat.Entity.MessageEntity;
import com.springproject.quickchat.Entity.UserEntity;
import com.springproject.quickchat.model.Message;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class MessageRepository implements MessageRepositoryInterface {
    private final JpaMessageRepository jpaMessageRepository;
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

    public MessageRepository(JpaMessageRepository jpaMessageRepository, DiscussionRepository discussionRepository, UserRepository userRepository) {
        this.jpaMessageRepository = jpaMessageRepository;
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(Message message) {
        DiscussionEntity discussionEntity = discussionRepository.findById(message.getDiscussionId())
                .orElseThrow(() -> new IllegalArgumentException("Discussion introuvable."));
        UserEntity senderEntity = userRepository.findById(message.getSender())
                .orElseThrow(() -> new IllegalArgumentException("Émetteur introuvable."));
        FileEntity fileEntity = message.getAttachedFile() != null
                ? FileEntity.fromFile(message.getAttachedFile().snapshot())
                : null;

        MessageEntity messageEntity = MessageEntity.fromMessage(message.snapshot(), discussionEntity, senderEntity, fileEntity);
        jpaMessageRepository.save(messageEntity);
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        return jpaMessageRepository.findById(messageId)
                .map(MessageEntity::toSnapshot)
                .map(Message::fromSnapshot);
    }

    @Override
    public List<Message> findByDiscussionId(Long discussionId) {
        return jpaMessageRepository.findAllByDiscussion_Id(discussionId).stream()
                .map(MessageEntity::toSnapshot)
                .map(Message::fromSnapshot)
                .collect(Collectors.toList());
    }
}
