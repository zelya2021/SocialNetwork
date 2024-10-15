package com.ana.app.chat.groupChat.entities;

import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.messages.entities.MessageEntity;
import com.ana.app.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nameOfChat;
    private TypeOfChat typeOfChat;

    @ManyToMany
    private Set<UserEntity> members = new HashSet<>();

    @OneToMany
    private Set<MessageEntity> messageEntity;
}
