package com.ana.app.user.Mappers;

import com.ana.app.user.DTOs.CreateUserDTO;
import com.ana.app.user.DTOs.UpdateUserDTO;
import com.ana.app.user.DTOs.UserResponseDTO;
import com.ana.app.user.Entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDTO toUserResponseDTO(UserEntity user);
    UserEntity fromCreateUserDTOtoUserEntity(CreateUserDTO userDto);
    UserResponseDTO updateUserDTOToUserResponseDTO(UpdateUserDTO dto);
}
