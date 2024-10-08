package com.ana.app.chat.groupChat.dto;

import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.user.dto.UserResponseDTO;
import com.ana.app.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatResponseDto {
    private long id;
    private String nameOfChat;
    private TypeOfChat typeOfChat;

    private Set<UserResponseDTO> members = new HashSet<>();

}
