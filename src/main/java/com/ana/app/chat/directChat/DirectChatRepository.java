package com.ana.app.chat.directChat;

import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectChatRepository extends JpaRepository<DirectChatEntity, Long> {

    @Query("SELECT d FROM DirectChatEntity d WHERE (d.user1 = :user1 AND d.user2 = :user2) OR (d.user1 = :user2 AND d.user2 = :user1)")
    Optional<DirectChatEntity> findByUsers(@Param("user1") UserEntity user1, @Param("user2") UserEntity user2);
}
