package com.ana.app.login;

import com.ana.app.common.SESV2EmailSender;
import com.ana.app.login.DTOs.LoginDTO;
import com.ana.app.login.exceptions.BadRequestException;
import com.ana.app.login.security.JwtResponse;
import com.ana.app.login.security.JwtUtil;
import com.ana.app.user.UserEntity;
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
            System.out.println("User not found!");
            throw new BadRequestException("User not found!");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
            throw new BadRequestException("Passwords don`t match!");
        }

        String token = JwtUtil.generateToken(loginDTO.getEmail());
        return new JwtResponse(token);
    }

    @Override
    public void sendForgotPasswordEmail(String email) {
        appService.setRecipientEmail(email);
        Random random = new Random();
        int resetPasswordCode = 10000 + random.nextInt(90000);
        appService.setResetPasswordCode(resetPasswordCode);

        var userEntity = userRepository.findByEmail(email);
        if(userEntity == null)
            throw new BadRequestException("User do not found!");

        userEntity.setResetPasswordCode(resetPasswordCode);
        userRepository.save(userEntity);

        appService.sendEmail();
    }
}
