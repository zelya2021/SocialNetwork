package com.ana.app.chat.groupChat;

import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.user.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChatEntity, Long> {
    // New method to find all group chats involving the current user
    @Query("SELECT g FROM GroupChatEntity g WHERE :user MEMBER OF g.members")
    HashSet<GroupChatEntity> findAllByMembers(@Param("user") UserEntity user);

    Page<GroupChatEntity> findAllByMembers(Pageable pageable, UserEntity user);

    Optional<GroupChatEntity> findById(Long id);
}
