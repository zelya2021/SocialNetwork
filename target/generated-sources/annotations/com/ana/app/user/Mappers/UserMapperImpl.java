package com.ana.app.user.Mappers;

import com.ana.app.user.DTOs.CreateUserDTO;
import com.ana.app.user.DTOs.CreateUserDTO.CreateUserDTOBuilder;
import com.ana.app.user.UserEntity;
import com.ana.app.user.UserEntity.UserEntityBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-25T20:49:10-0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public CreateUserDTO toDTO(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        CreateUserDTOBuilder createUserDTO = CreateUserDTO.builder();

        createUserDTO.name( user.getName() );
        createUserDTO.email( user.getEmail() );
        createUserDTO.password( user.getPassword() );

        return createUserDTO.build();
    }

    @Override
    public UserEntity toEntity(CreateUserDTO userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.name( userDto.getName() );
        userEntity.email( userDto.getEmail() );
        userEntity.password( userDto.getPassword() );

        return userEntity.build();
    }
}
