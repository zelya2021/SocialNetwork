package com.ana.app.user;

import com.ana.app.common.DTOs.StatusDTO;
import com.ana.app.user.DTOs.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/me")
    public ResponseEntity<?> authenticate() {
        return ResponseEntity.ok(userService.getMe());
    }

    @PutMapping("/me")
    public UserResponseDTO editCurrentUser(@Valid @RequestBody UpdateUserDTO user) {

        return userService.editCurrentUser(user);
    }

    @PostMapping()
    public ResponseDTO createUser(@Valid @RequestBody CreateUserDTO user)
    {
        return userService.createUser(user);
    }

    @GetMapping("/")
    public List<UserEntity> fetchUsersList()
    {
        return userService.fetchUserList();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable("id") Long id)
    {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@RequestBody UpdateUserDTO user, @PathVariable("id") Long id)
    {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public StatusDTO deleteUserById(@PathVariable("id") Long id)
    {
        userService.deleteUserById(id);
        return new StatusDTO("Deleted Successfully");
    }

    @PutMapping("/change-password")
    public StatusDTO changeUserPassword(@RequestBody ChangeUserPasswordDTO user)
    {
        userService.changeUserPassword(user);
        return new StatusDTO("Password changed Successfully!");
    }
}
