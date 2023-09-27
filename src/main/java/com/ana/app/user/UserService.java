package com.ana.app.user;

import com.ana.app.user.DTOs.CreateUserDTO;
import com.ana.app.user.DTOs.ResponseDTO;

import java.util.List;

public interface UserService {

    // Save operation
//    UserEntity saveUser(UserEntity user);

    // Read operation
    List<UserEntity> fetchUserList();

    // Update operation
    UserEntity updateUser(UserEntity user, Long userId);

    ResponseDTO createUser (CreateUserDTO user);

    // Delete operation
    void deleteUserById(Long userId);
}
