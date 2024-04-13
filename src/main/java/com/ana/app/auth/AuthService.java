package com.ana.app.auth;

import com.ana.app.auth.DTOs.ForgotPasswordDTO;
import com.ana.app.auth.DTOs.LoginDTO;
import com.ana.app.auth.DTOs.ResetPasswordDTO;
import com.ana.app.auth.security.JwtResponse;

public interface AuthService {
    JwtResponse authenticate(LoginDTO loginDTO);
    void sendForgotPasswordEmail(ForgotPasswordDTO forgotPasswordDTO);
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
