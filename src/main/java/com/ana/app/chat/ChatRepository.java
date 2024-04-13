package com.ana.app.chat;

import com.ana.app.chat.Entities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("SELECT c FROM ChatEntity c JOIN c.members m WHERE m.id IN :membersIds")
    Optional<ChatEntity> findByMemberIds(@Param("membersIds") Set<Long> membersIds);
}
