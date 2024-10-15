package com.ana.app.messages.entities;

import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime time;

    private String message;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private DirectChatEntity directChat;

    @ManyToOne
    private GroupChatEntity groupChat;
}
