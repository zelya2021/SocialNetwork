package com.ana.app.messages;

import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.messages.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository
        extends JpaRepository<MessageEntity, Long> {
    boolean existsByIdAndAuthorId(Long messageId, Long userId);
    List<MessageEntity> findByDirectChat(DirectChatEntity directChat);
    List<MessageEntity> findByGroupChat(GroupChatEntity groupChat);
}
