package com.ana.app.user.entities;

import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.messages.entities.MessageEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "auth_id", unique = true, updatable = false, nullable = false)
    private String authId;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Integer resetPasswordCode;

    @ManyToMany
    private Set<UserEntity> friends = new HashSet<>();

    @OneToMany
    private Set<DirectChatEntity> directChats = new HashSet<>();

    @ManyToMany
    private Set<GroupChatEntity> groupChats = new HashSet<>();

    @OneToMany
    private Set<MessageEntity> messages = new HashSet<>();

    public UserEntity(long id, String name, String lastName, String email, String password){
        this.id = id;
        this.authId = UUID.randomUUID().toString();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
