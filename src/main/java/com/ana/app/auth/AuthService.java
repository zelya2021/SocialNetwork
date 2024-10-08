package com.ana.app.auth;

import com.ana.app.auth.dto.ForgotPasswordDTO;
import com.ana.app.auth.dto.LoginDTO;
import com.ana.app.auth.dto.ResetPasswordDTO;
import com.ana.app.auth.security.JwtResponse;

public interface AuthService {
    JwtResponse authenticate(LoginDTO loginDTO);
    void sendForgotPasswordEmail(ForgotPasswordDTO forgotPasswordDTO);
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
