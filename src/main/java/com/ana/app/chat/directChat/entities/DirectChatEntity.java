package com.ana.app.chat.directChat.entities;

import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private TypeOfChat typeOfChat;

    @ManyToOne
    private UserEntity user1;
    @ManyToOne
    private UserEntity user2;
}
