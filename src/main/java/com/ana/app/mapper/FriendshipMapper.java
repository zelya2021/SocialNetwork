package com.ana.app.mapper;

import com.ana.app.friendship.DTOs.FriendshipResponseDTO;
import com.ana.app.friendship.entities.FriendshipRequestEntity;
import com.ana.app.user.Mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserMapper.class)
public interface FriendshipMapper {
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "recipient", target = "recipient")
    FriendshipResponseDTO friendshipRequestEntityToFriendshipResponseDTO(FriendshipRequestEntity entity);
}
