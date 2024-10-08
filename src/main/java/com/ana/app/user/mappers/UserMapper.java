package com.ana.app.user.mappers;

import com.ana.app.user.dto.CreateUserDTO;
import com.ana.app.user.dto.UpdateUserDTO;
import com.ana.app.user.dto.UserResponseDTO;
import com.ana.app.user.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDTO fromUserEntityToUserResponseDTO(UserEntity user);
    UserEntity fromCreateUserDTOtoUserEntity(CreateUserDTO userDto);
    UserEntity fromUpdateUserDTOtoUserEntity(UpdateUserDTO userDto);
}
