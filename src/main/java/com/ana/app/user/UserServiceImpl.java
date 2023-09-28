package com.ana.app.user;

import com.ana.app.login.exceptions.BadRequestException;
import com.ana.app.user.DTOs.CreateUserDTO;
import com.ana.app.user.DTOs.ResponseDTO;
import com.ana.app.user.DTOs.ResponseStatusEnum;
import com.ana.app.user.Mappers.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

//    @Override
//    public UserEntity saveUser(UserEntity user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }

    @Override
    public List<UserEntity> fetchUserList() {
        return (List<UserEntity>) userRepository.findAll();
    }

    @Override
    public UserEntity updateUser(UserEntity user, Long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();

        if(Objects.nonNull(user.getName()) && !"".equalsIgnoreCase(user.getName())){
            userEntity.setName(userEntity.getName());
        }

        if(Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())){
            userEntity.setEmail(userEntity.getEmail());
        }

        return userRepository.save(userEntity);
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
}
