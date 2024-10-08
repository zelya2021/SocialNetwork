package com.ana.app.chat.groupChat;

import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChatEntity, Long> {
}
