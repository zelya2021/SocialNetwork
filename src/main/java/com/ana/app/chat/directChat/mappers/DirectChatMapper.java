package com.ana.app.chat.directChat.mappers;

import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DirectChatMapper {
    @Mapping(target = "typeOfChat", source = "typeOfChat")
    DirectChatResponseDto fromDirectChatEntityToDirectChatResponseDTO(DirectChatEntity chatEntity);
    DirectChatResponseDto userEntityToDirectChatResponseDTO(UserEntity userEntity);
}
