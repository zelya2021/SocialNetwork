package com.ana.app.auth;

import com.ana.app.auth.dto.ForgotPasswordDTO;
import com.ana.app.auth.dto.LoginDTO;
import com.ana.app.auth.dto.ResetPasswordDTO;
import com.ana.app.auth.security.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Management", description = "User management operations")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user") })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginDTO loginDto) {
        return ResponseEntity.ok(authService.authenticate(loginDto));
    }

    @Operation(summary = "Forgot password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User does not found!") })
    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        authService.sendForgotPasswordEmail(forgotPasswordDTO);
        // TODO: Change response body from string to general JSON object with "message" field with ResponseEntity
        return "Password reset email sent!";
    }

    @Operation(summary = "Reset password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Confirmation code is not correct!") })
    @PostMapping("/reset-password")
    public String resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        authService.resetPassword(resetPasswordDTO);
        // TODO: Change response body from string to general JSON object with "message" field with ResponseEntity
        return "Password reset sent successfully!";
    }
}
