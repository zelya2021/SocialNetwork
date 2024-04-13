package com.ana.app.user;

import com.ana.app.common.DTOs.PaginatedResponseDTO;
import com.ana.app.auth.exceptions.BadRequestException;
import com.ana.app.user.DTOs.*;
import com.ana.app.user.Entities.UserEntity;
import com.ana.app.user.Mappers.UserMapper;
import io.jsonwebtoken.lang.Strings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Override
    @CachePut(value = "users", key = "#userId")
    public UserResponseDTO updateUser(UpdateUserDTO userDto, Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("User with provided id does not exist");
        }

        var userEntity = userEntityOptional.get();

        if(Objects.nonNull(userDto.getLastName()) && Strings.hasLength(userDto.getLastName())){
            userEntity.setLastName(userDto.getLastName());
        }

        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());

        userRepository.save(userEntity);
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    public UserResponseDTO getMe() {
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userEntity = userRepository.findByEmail(userDetails.getUsername());
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    public UserResponseDTO createUser(CreateUserDTO userDTO) {
        var existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser != null)
            throw new BadRequestException("User already exist!");
        UserEntity userEntity = userMapper.fromCreateUserDTOtoUserEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    @CacheEvict(value = "users", key = "#userId")
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
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

    public UserResponseDTO editCurrentUser(UpdateUserDTO user){
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername());
        userEntity.setName(user.getName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userRepository.save(userEntity);
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Cacheable(value = "users", key = "#userId")
    public UserResponseDTO getUserById(Long userId){
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("User with provided id does not exist!");
        }

        var userEntity = userEntityOptional.get();
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Cacheable(value = "usersPageCache", key = "'usersPage:' + #pageNo + ':' + #pageSize")
    public PaginatedResponseDTO<UserResponseDTO> findPaginatedDTO(int pageNo, int pageSize) {
        Page<UserEntity> userPage = userRepository.findAll(PageRequest.of(pageNo - 1, pageSize));
        List<UserResponseDTO> userDTOs = userPage.getContent().stream()
                .map(user -> new UserResponseDTO(user.getId(),user.getName(), user.getLastName(), user.getEmail()))
                .collect(Collectors.toList());

        PaginatedResponseDTO<UserResponseDTO> userPageDTO = new PaginatedResponseDTO<>();
        userPageDTO.setUsers(userDTOs);
        userPageDTO.setPageNumber(userPage.getNumber() + 1); // +1 to adjust for zero-based pages
        userPageDTO.setPageSize(userPage.getSize());
        userPageDTO.setTotalElements(userPage.getTotalElements());
        userPageDTO.setTotalPages(userPage.getTotalPages());

        return userPageDTO;
    }
}
