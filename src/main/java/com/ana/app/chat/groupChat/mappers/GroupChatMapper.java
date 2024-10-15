package com.ana.app.chat.groupChat.mappers;

import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import org.mapstruct.Mapper;

@Mapper
public interface GroupChatMapper {
    GroupChatResponseDto fromGroupChatEntityToGroupChatResponseDto(GroupChatEntity chatEntity);
}
