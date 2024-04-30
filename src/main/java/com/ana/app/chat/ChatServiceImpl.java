package com.ana.app.chat;

import com.ana.app.auth.exceptions.BadRequestException;
import com.ana.app.chat.DTOs.ChatResponseDTO;
import com.ana.app.chat.DTOs.CreateDirectChatDTO;
import com.ana.app.chat.DTOs.CreateGroupChatDTO;
import com.ana.app.chat.DTOs.DeleteChatDTO;
import com.ana.app.chat.Entities.ChatEntity;
import com.ana.app.chat.Mappers.ChatMapper;
import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.common.DTOs.PaginatedResponseDTO;
import com.ana.app.user.DTOs.UserResponseDTO;
import com.ana.app.user.Entities.UserEntity;
import com.ana.app.user.Mappers.UserMapper;
import com.ana.app.user.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    private static final ChatMapper chatMapper = Mappers.getMapper(ChatMapper.class);
    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public ChatResponseDTO createDirectChat(CreateDirectChatDTO directChatDTO) {
        Optional<UserEntity> userEntity = userRepository.findById(directChatDTO.getUserId());
        if(userEntity.isEmpty())
            throw new BadRequestException("User with provided id not found!");

        var user = userEntity.get();

        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity currentUserEntity = userRepository.findByEmail(userDetails.getUsername());

        Set<UserEntity> setOfMembers = new HashSet<>();
        setOfMembers.add(user);
        setOfMembers.add(currentUserEntity);

        Optional<ChatEntity> duplicateChatEntity = chatRepository.findByMemberIds(
                setOfMembers.stream().map(UserEntity::getId).collect(Collectors.toSet())
        );
        if(duplicateChatEntity.isPresent())
            throw new BadRequestException("Chat with this user id already exist!");

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMembers(setOfMembers);
        chatEntity.setNameOfChat(TypeOfChat.DIRECT.toString());
        chatEntity.setTypeOfChat(TypeOfChat.DIRECT);

        chatRepository.save(chatEntity);
        return chatMapper.fromChatEntityToChatResponseDTO(chatEntity);
    }

    public ChatResponseDTO createGroupChat(CreateGroupChatDTO groupChatDTO) {
        var userEntities = userRepository.findByIdIn(groupChatDTO.getMembersIds());

        if(userEntities.isEmpty() || userEntities.size() != groupChatDTO.getMembersIds().size())
            throw new BadRequestException("User/users with provided ids does not found!");

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setNameOfChat(groupChatDTO.getNameOfChat());
        chatEntity.setMembers(userEntities);
        chatEntity.setTypeOfChat(TypeOfChat.GROUP);

        chatRepository.save(chatEntity);
        return chatMapper.fromChatEntityToChatResponseDTO(chatEntity);
    }

    public ChatResponseDTO updateGroupChat(Long id, CreateGroupChatDTO updateChatDTO) {
        Optional<ChatEntity> chatEntityOptional = chatRepository.findById(id);
        if(chatEntityOptional.isEmpty())
            throw new BadRequestException("Chat with this id does not exist!");

        var userEntities = userRepository.findByIdIn(updateChatDTO.getMembersIds());
        if(userEntities.isEmpty() || userEntities.size() != updateChatDTO.getMembersIds().size())
            throw new BadRequestException("User/users with provided ids not found!");

        var chatEntity = chatEntityOptional.get();
        chatEntity.setNameOfChat(updateChatDTO.getNameOfChat());
        chatEntity.setMembers(userEntities);
        chatEntity.setTypeOfChat(TypeOfChat.GROUP);

        chatRepository.save(chatEntity);
        return chatMapper.fromChatEntityToChatResponseDTO(chatEntity);
    }

    public DeleteChatDTO deleteChat(Long id) {
        Optional<ChatEntity> IsChatEntity = chatRepository.findById(id);
        if(IsChatEntity.isEmpty())
            throw new BadRequestException("Chat with this id does not exist!");

        var chatEntity = IsChatEntity.get();
        chatRepository.delete(chatEntity);

        return new DeleteChatDTO(chatEntity.getId());
    }

    public ChatResponseDTO getChat(Long id) {
        Optional<ChatEntity> chatEntity = chatRepository.findById(id);
        if(chatEntity.isEmpty())
            throw new BadRequestException("Chat with this id does not exist!");

        return chatMapper.fromChatEntityToChatResponseDTO(chatEntity.get());
    }

    public PaginatedResponseDTO<ChatResponseDTO> getChats (int pageNo, int pageSize) {
        Page<ChatEntity> chatEntityPage = chatRepository.findAll(PageRequest.of(pageNo - 1, pageSize));

        List<ChatResponseDTO> chatsDTOs = chatEntityPage.getContent().stream()
                .map(chat -> new ChatResponseDTO(
                        chat.getId(),
                        chat.getNameOfChat(),
                        chat.getTypeOfChat(),
                        chat.getMembers().stream()
                                .map(user -> userMapper.toUserResponseDTO(user))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());

        PaginatedResponseDTO<ChatResponseDTO> chatPageDTO = new PaginatedResponseDTO<>();
        chatPageDTO.setUsers(chatsDTOs);
        chatPageDTO.setPageNumber(chatEntityPage.getNumber() + 1); // +1 to adjust for zero-based pages
        chatPageDTO.setPageSize(chatEntityPage.getSize());
        chatPageDTO.setTotalElements(chatEntityPage.getTotalElements());
        chatPageDTO.setTotalPages(chatEntityPage.getTotalPages());

        return chatPageDTO;
    }
}
