package com.ana.app.login;

import com.ana.app.login.DTOs.LoginDTO;
import com.ana.app.login.security.JwtResponse;

public interface AuthService {
    JwtResponse authenticate(LoginDTO loginDTO);
}
