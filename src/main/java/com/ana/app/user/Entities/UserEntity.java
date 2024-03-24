package com.ana.app.user.Entities;

import com.ana.app.friend.entities.FriendRequestEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Integer resetPasswordCode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequestEntity> friendRequestList;
}
