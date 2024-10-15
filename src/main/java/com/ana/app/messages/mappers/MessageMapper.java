package com.ana.app.messages.mappers;

import com.ana.app.chat.directChat.mappers.DirectChatMapper;
import com.ana.app.chat.groupChat.mappers.GroupChatMapper;
import com.ana.app.messages.dto.MessageResponseDTO;
import com.ana.app.messages.entities.MessageEntity;
import com.ana.app.user.mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class, DirectChatMapper.class, GroupChatMapper.class})
public interface MessageMapper {
    MessageResponseDTO fromMessageEntityToMessageResponseDTO (MessageEntity messageEntity);
}
