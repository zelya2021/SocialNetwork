package com.ana.app.user;

import com.ana.app.user.DTOs.CreateUserDTO;
import com.ana.app.user.DTOs.ResponseDTO;
import com.ana.app.user.DTOs.UpdateUserDTO;
import com.ana.app.user.DTOs.UserResponseDTO;
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

    @PostMapping()
    public ResponseDTO createUser(@Valid @RequestBody CreateUserDTO user)
    {
        return userService.createUser(user);
    }

    @GetMapping()
    public List<UserEntity> fetchUsersList()
    {
        return userService.fetchUserList();
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@RequestBody UpdateUserDTO user, @PathVariable("id") Long id)
    {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartmentById(@PathVariable("id") Long id)
    {
        userService.deleteUserById(id);
        return "Deleted Successfully";
    }
}
