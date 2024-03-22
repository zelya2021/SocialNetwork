package com.ana.app.user;

import com.ana.app.user.DTOs.*;

import java.util.List;

public interface UserService {
    List<UserEntity> fetchUserList();

    UserResponseDTO updateUser(UpdateUserDTO user, Long userId);

    UserResponseDTO getMe();

    ResponseDTO createUser (CreateUserDTO user);

    void deleteUserById(Long userId);

    void changeUserPassword(ChangeUserPasswordDTO user);
}
