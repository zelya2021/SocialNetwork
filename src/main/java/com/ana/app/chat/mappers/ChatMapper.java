package com.ana.app.chat.mappers;

import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ChatMapper {

    @Mapping(target = "typeOfChat", source = "typeOfChat")
    DirectChatResponseDto fromDirectChatEntityToDirectChatResponseDTO(DirectChatEntity chatEntity);
    GroupChatResponseDto fromGroupChatEntityToGroupChatResponseDto(GroupChatEntity chatEntity);
    DirectChatResponseDto userEntityToDirectChatResponseDTO(UserEntity userEntity);
}
