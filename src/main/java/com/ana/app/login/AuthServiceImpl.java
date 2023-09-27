package com.ana.app.login;

import com.ana.app.login.DTOs.LoginDTO;
import com.ana.app.login.exceptions.BadRequestException;
import com.ana.app.login.security.JwtResponse;
import com.ana.app.login.security.JwtUtil;
import com.ana.app.user.UserEntity;
import com.ana.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
}
