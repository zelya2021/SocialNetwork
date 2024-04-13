package com.ana.app.user;

import com.ana.app.common.DTOs.PaginatedResponseDTO;
import com.ana.app.user.DTOs.*;

public interface UserService {

    UserResponseDTO updateUser(UpdateUserDTO user, Long userId);

    UserResponseDTO getMe();

    UserResponseDTO createUser (CreateUserDTO user);

    void deleteUserById(Long userId);

    void changeUserPassword(ChangeUserPasswordDTO user);
    UserResponseDTO editCurrentUser(UpdateUserDTO user);
    UserResponseDTO getUserById(Long userId);
    PaginatedResponseDTO<UserResponseDTO> findPaginatedDTO(int pageNo, int pageSize);
}
