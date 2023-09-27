package com.ana.app.login;

import com.ana.app.login.DTOs.LoginDTO;
import com.ana.app.login.security.JwtResponse;
import com.ana.app.login.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginDTO loginDto) {
        return ResponseEntity.ok(authService.authenticate(loginDto));
    }
}
