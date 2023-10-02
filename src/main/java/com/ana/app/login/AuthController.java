package com.ana.app.login;

import com.ana.app.login.DTOs.ForgotPasswordDTO;
import com.ana.app.login.DTOs.LoginDTO;
import com.ana.app.login.DTOs.ResetPasswordDTO;
import com.ana.app.login.security.JwtResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginDTO loginDto) {
        return ResponseEntity.ok(authService.authenticate(loginDto));
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        authService.sendForgotPasswordEmail(forgotPasswordDTO);
        return "Password reset email sent!";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        authService.resetPassword(resetPasswordDTO);
        return "Password reset sent successfully!";
    }
}
