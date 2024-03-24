package com.ana.app.user;

import com.ana.app.user.DTOs.*;
import com.ana.app.user.Entities.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> fetchUserList();

    UserResponseDTO updateUser(UpdateUserDTO user, Long userId);

    UserResponseDTO getMe();

    ResponseDTO createUser (CreateUserDTO user);

    void deleteUserById(Long userId);

    void changeUserPassword(ChangeUserPasswordDTO user);
    UserResponseDTO editCurrentUser(UpdateUserDTO user);
    UserResponseDTO getUserById(Long userId);
    UserPageDTO findPaginatedDTO(int pageNo, int pageSize);
}
