package com.ana.app.user;

import com.ana.app.common.DTOs.PaginatedResponseDTO;
import com.ana.app.common.DTOs.StatusDTO;
import com.ana.app.user.DTOs.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Management", description = "User management operations")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Operation(summary = "Get current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user") })
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(userService.getMe());
    }

    @Operation(summary = "Update current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user") })
    @PutMapping("/me")
    public UserResponseDTO editCurrentUser(@Valid @RequestBody UpdateUserDTO user) {

        return userService.editCurrentUser(user);
    }

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User already exist!") })
    @PostMapping()
    public UserResponseDTO createUser(@Valid @RequestBody CreateUserDTO user)
    {
        return userService.createUser(user);
    }

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User with provided id does not exist!") })
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable("id") Long id)
    {
        return userService.getUserById(id);
    }

    @Operation(summary = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User with provided id does not exist!") })
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@RequestBody UpdateUserDTO user, @PathVariable("id") Long id)
    {
        return userService.updateUser(user, id);
    }

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user") })
    @DeleteMapping("/{id}")
    public StatusDTO deleteUserById(@PathVariable("id") Long id)
    {
        userService.deleteUserById(id);
        return new StatusDTO("Deleted Successfully");
    }

    @Operation(summary = "Change current user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Invalid old user's password") })
    @PutMapping("/change-password")
    public StatusDTO changeUserPassword(@RequestBody ChangeUserPasswordDTO user)
    {
        userService.changeUserPassword(user);
        return new StatusDTO("Password changed Successfully!");
    }

    @Operation(summary = "List all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user") })
    @GetMapping("/")
    public PaginatedResponseDTO<UserResponseDTO> getAllUsers(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "50") int limit) {
        return userService.findPaginatedDTO(page, limit);
    }
}
