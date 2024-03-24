package com.ana.app.friend;

import com.ana.app.friend.entities.FriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository  extends JpaRepository<FriendRequestEntity, Long> {

    Optional<FriendRequestEntity> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}
