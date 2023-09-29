package com.ana.app.user.Mappers;

import com.ana.app.user.DTOs.CreateUserDTO;
import com.ana.app.user.DTOs.UserResponseDTO;
import com.ana.app.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    CreateUserDTO toDTO(UserEntity user);
    UserResponseDTO toUserResponseDTO(UserEntity user);
    UserEntity toEntity(CreateUserDTO userDto);
}
