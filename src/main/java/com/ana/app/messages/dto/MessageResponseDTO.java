package com.ana.app.messages.dto;

import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.user.dto.UserResponseDTO;
import com.ana.app.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {
    private long id;

    private UserResponseDTO author;

    private LocalDateTime time;

    private long chatId;

    private TypeOfChat typeOfChat;

    private String message;
}
