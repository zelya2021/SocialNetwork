package com.ana.app.chat.directChat.dto;

import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.user.dto.UserResponseDTO;
import com.ana.app.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectChatResponseDto {
    private long id;
    private TypeOfChat typeOfChat;

    private UserResponseDTO user1;
    private UserResponseDTO user2;
}
