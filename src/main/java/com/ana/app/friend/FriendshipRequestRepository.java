package com.ana.app.friend;

import com.ana.app.friend.entities.FriendshipRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequestEntity, Long> {

    Optional<FriendshipRequestEntity> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
    List<FriendshipRequestEntity> findByRecipientId(Long recipientId);
}
