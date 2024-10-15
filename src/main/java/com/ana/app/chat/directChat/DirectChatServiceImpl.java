package com.ana.app.chat.directChat;

import com.ana.app.auth.exceptions.BadRequestException;
import com.ana.app.chat.DeleteChatDTO;
import com.ana.app.chat.directChat.dto.CreateDirectChatDTO;
import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.directChat.mappers.DirectChatMapper;
import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.common.dto.PaginatedResponseDTO;
import com.ana.app.user.UserRepository;
import com.ana.app.user.entities.UserEntity;
import com.ana.app.user.mappers.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectChatServiceImpl implements DirectChatService{
    @Autowired
    private DirectChatRepository directChatRepository;
    @Autowired
    private UserRepository userRepository;

    private static final DirectChatMapper chatMapper = Mappers.getMapper(DirectChatMapper.class);
    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public DirectChatResponseDto createDirectChat(CreateDirectChatDTO directChatDTO) {
        Optional<UserEntity> userEntity = userRepository.findById(directChatDTO.getUserId());
        if(userEntity.isEmpty())
            throw new BadRequestException("User with provided id not found!");

        var user = userEntity.get();

        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity currentUserEntity = userRepository.findByEmail(userDetails.getUsername());

        Optional<DirectChatEntity> directChatEntity = directChatRepository.findByUsers(user, currentUserEntity);

        if(directChatEntity.isPresent())
            throw new BadRequestException("Chat with this user id already exist!");

        DirectChatEntity directChatEntity1 = new DirectChatEntity();
        directChatEntity1.setUser2(user);
        directChatEntity1.setUser1(currentUserEntity);
        directChatEntity1.setTypeOfChat(TypeOfChat.DIRECT);

        directChatRepository.save(directChatEntity1);
        return chatMapper.fromDirectChatEntityToDirectChatResponseDTO(directChatEntity1);
    }

    public DeleteChatDTO deleteDirectChat(Long id) {
        Optional<DirectChatEntity> IsChatEntity = directChatRepository.findById(id);
        if(IsChatEntity.isEmpty())
            throw new BadRequestException("Chat with this id does not exist!");

        var chatEntity = IsChatEntity.get();
        directChatRepository.delete(chatEntity);

        return new DeleteChatDTO(chatEntity.getId());
    }

    public DirectChatResponseDto getDirectChat(Long id) {
        Optional<DirectChatEntity> chatEntity = directChatRepository.findById(id);
        if(chatEntity.isEmpty())
            throw new BadRequestException("Chat with this id does not exist!");

        return chatMapper.fromDirectChatEntityToDirectChatResponseDTO(chatEntity.get());
    }

    public PaginatedResponseDTO<DirectChatResponseDto> getDirectChats (int pageNo, int pageSize) {
        Page<DirectChatEntity> chatEntityPage = directChatRepository.findAll(PageRequest.of(pageNo - 1, pageSize));

        List<DirectChatResponseDto> chatsDTOs = chatEntityPage.getContent().stream()
                .map(chat -> new DirectChatResponseDto(
                        chat.getId(),
                        chat.getTypeOfChat(),
                        userMapper.fromUserEntityToUserResponseDTO(chat.getUser1()),
                        userMapper.fromUserEntityToUserResponseDTO(chat.getUser2())
                ))
                .collect(Collectors.toList());

        PaginatedResponseDTO<DirectChatResponseDto> directChatPageDTO = new PaginatedResponseDTO<>();

        directChatPageDTO.setUsers(chatsDTOs);
        directChatPageDTO.setPageNumber(chatEntityPage.getNumber() + 1); // +1 to adjust for zero-based pages
        directChatPageDTO.setPageSize(chatEntityPage.getSize());
        directChatPageDTO.setTotalElements(chatEntityPage.getTotalElements());
        directChatPageDTO.setTotalPages(chatEntityPage.getTotalPages());

        return directChatPageDTO;
    }
}
