package com.ana.app.chat.Mappers;

import com.ana.app.chat.DTOs.ChatResponseDTO;
import com.ana.app.chat.Entities.ChatEntity;
import com.ana.app.user.DTOs.UserResponseDTO;
import com.ana.app.user.Entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ChatMapper {
    @Mapping(target = "members", source = "members")
    @Mapping(target = "typeOfChat", source = "typeOfChat")
    ChatResponseDTO fromChatEntityToChatResponseDTO(ChatEntity chatEntity);
    UserResponseDTO userEntityToUserResponseDTO(UserEntity userEntity);
}
