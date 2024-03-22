package com.ana.app.user;

import com.ana.app.login.exceptions.BadRequestException;
import com.ana.app.user.DTOs.*;
import com.ana.app.user.Mappers.UserMapper;
import io.jsonwebtoken.lang.Strings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Override
    public List<UserEntity> fetchUserList() {
        return (List<UserEntity>) userRepository.findAll();
    }

    @Override
    public UserResponseDTO updateUser(UpdateUserDTO userDto, Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (!userEntityOptional.isPresent()) {
            throw new BadRequestException("User do not exist!");
        }

        var userEntity = userEntityOptional.get();

        if(Objects.nonNull(userDto.getLastName()) && Strings.hasLength(userDto.getLastName())){
            userEntity.setLastName(userDto.getLastName());
        }

        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());

        userRepository.save(userEntity);
        return getUserResponseDTO(userEntity);
    }

    @Override
    public UserResponseDTO getMe() {
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userEntity = userRepository.findByEmail(userDetails.getUsername());
        return UserResponseDTO.builder()
                .name(userEntity.getName())
                .lastName((userEntity.getLastName()))
                .email(userEntity.getEmail()).build();
    }

    @Override
    public ResponseDTO createUser(CreateUserDTO userDTO) {
        var existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser != null)
            throw new BadRequestException("User already exist!");
        UserEntity userEntity = getUserEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
        return new ResponseDTO(ResponseStatusEnum.USER_CREATED);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserEntity getUserEntity(CreateUserDTO user) {
        return userMapper.toEntity(user);
    }
    public UserResponseDTO getUserResponseDTO(UserEntity user) {
        return userMapper.toUserResponseDTO(user);
    }

    public void changeUserPassword(ChangeUserPasswordDTO user){
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(passwordEncoder.matches(user.oldPassword, userDetails.getPassword()))
        {
            UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername());
            userEntity.setPassword(passwordEncoder.encode(user.newPassword));
            userRepository.save(userEntity);
        }
        else {
            throw new BadRequestException("Invalid old password");
        }
    }
}
