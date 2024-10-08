package com.ana.app.auth;

import com.ana.app.common.SESV2EmailSender;
import com.ana.app.auth.dto.ForgotPasswordDTO;
import com.ana.app.auth.dto.LoginDTO;
import com.ana.app.auth.dto.ResetPasswordDTO;
import com.ana.app.auth.exceptions.BadRequestException;
import com.ana.app.auth.security.JwtResponse;
import com.ana.app.auth.security.JwtUtil;
import com.ana.app.user.entities.UserEntity;
import com.ana.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final SESV2EmailSender appService;

    public AuthServiceImpl(SESV2EmailSender appService) {
        this.appService = appService;
    }

    @Override
    public JwtResponse authenticate(LoginDTO loginDTO) {
        UserEntity userEntity = userRepository.findByEmail(loginDTO.getEmail());
        System.out.println("user entity" + userEntity);
        if (userEntity == null) {
            // TODO: Configure spring-boot logger and use logger with info / error / warn log calls instead of System.out.println
            System.out.println("User not found!");
            throw new BadRequestException("User not found!");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
            throw new BadRequestException("Incorrect password!");
        }

        String token = JwtUtil.generateToken(loginDTO.getEmail());
        return new JwtResponse(token);
    }

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordDTO forgotPasswordDTO) {
        appService.setRecipientEmail(forgotPasswordDTO.getEmail());
        Random random = new Random();
        // TODO: Extract reset password code generation in dedicated function to simplify future changes
        int resetPasswordCode = 10000 + random.nextInt(90000);
        appService.setResetPasswordCode(resetPasswordCode);

        var userEntity = userRepository.findByEmail(forgotPasswordDTO.getEmail());
        if(userEntity == null)
            throw new BadRequestException("User does not found!");

        userEntity.setResetPasswordCode(resetPasswordCode);
        userRepository.save(userEntity);

        appService.sendEmail();
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        UserEntity userEntity = userRepository.findByResetPasswordCode(resetPasswordDTO.getResetPasswordCode());
        if(userEntity == null)
            throw new BadRequestException("Confirmation code is not correct!");

        userEntity.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userEntity.setResetPasswordCode(null);
        userRepository.save(userEntity);
    }
}
