package com.ana.app.friendship.entities;

import com.ana.app.friendship.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.user.Entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private StatusOfFriendshipRequestEnum status;
    private Date timeOfReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recepient_id")
    private UserEntity recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

}
