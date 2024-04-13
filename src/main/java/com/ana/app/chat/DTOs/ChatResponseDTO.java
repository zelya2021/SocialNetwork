package com.ana.app.chat.DTOs;

import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.user.DTOs.UserResponseDTO;
import com.ana.app.user.Entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDTO {
    private long id;
    private String nameOfChat;
    private TypeOfChat typeOfChat;
    private Set<UserResponseDTO> members;
}
