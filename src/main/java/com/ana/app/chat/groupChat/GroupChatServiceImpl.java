package com.ana.app.chat.groupChat;

import com.ana.app.auth.exceptions.BadRequestException;
import com.ana.app.chat.DeleteChatDTO;
import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.chat.groupChat.dto.CreateGroupChatDTO;
import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.chat.mappers.ChatMapper;
import com.ana.app.common.dto.PaginatedResponseDTO;
import com.ana.app.user.UserRepository;
import com.ana.app.user.mappers.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupChatServiceImpl implements GroupChatService{
    @Autowired
    private GroupChatRepository groupChatRepository;
    @Autowired
    private UserRepository userRepository;

    private static final ChatMapper chatMapper = Mappers.getMapper(ChatMapper.class);
    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public GroupChatResponseDto createGroupChat(CreateGroupChatDTO groupChatDTO) {
        var userEntities = userRepository.findByIdIn(groupChatDTO.getMembersIds());

        if(userEntities.isEmpty() || userEntities.size() != groupChatDTO.getMembersIds().size())
            throw new BadRequestException("User/users with provided ids does not found!");

        GroupChatEntity groupChatEntity = new GroupChatEntity();
        groupChatEntity.setNameOfChat(groupChatDTO.getNameOfChat());
        groupChatEntity.setMembers(userEntities);
        groupChatEntity.setTypeOfChat(TypeOfChat.GROUP);

        groupChatRepository.save(groupChatEntity);
        return chatMapper.fromGroupChatEntityToGroupChatResponseDto(groupChatEntity);
    }

    public GroupChatResponseDto updateGroupChat(Long id, CreateGroupChatDTO updateChatDTO) {
        Optional<GroupChatEntity> chatEntityOptional = groupChatRepository.findById(id);
        if(chatEntityOptional.isEmpty())
            throw new BadRequestException("Chat with this id does not exist!");

        var userEntities = userRepository.findByIdIn(updateChatDTO.getMembersIds());
        if(userEntities.isEmpty() || userEntities.size() != updateChatDTO.getMembersIds().size())
            throw new BadRequestException("User/users with provided ids not found!");

        var groupChatEntity = chatEntityOptional.get();
        groupChatEntity.setNameOfChat(updateChatDTO.getNameOfChat());
        groupChatEntity.setMembers(userEntities);
        groupChatEntity.setTypeOfChat(TypeOfChat.GROUP);

        groupChatRepository.save(groupChatEntity);
        return chatMapper.fromGroupChatEntityToGroupChatResponseDto(groupChatEntity);
    }

    public DeleteChatDTO deleteGroupChat(Long id) {
        Optional<GroupChatEntity> IsChatEntity = groupChatRepository.findById(id);
        if(IsChatEntity.isEmpty())
            throw new BadRequestException("Chat with this id does not exist!");

        var chatEntity = IsChatEntity.get();
        groupChatRepository.delete(chatEntity);

        return new DeleteChatDTO(chatEntity.getId());
    }

    public GroupChatResponseDto getGroupChat(Long id) {
        Optional<GroupChatEntity> chatEntity = groupChatRepository.findById(id);
        if(chatEntity.isEmpty())
            throw new BadRequestException("Group chat with this id does not exist!");

        return chatMapper.fromGroupChatEntityToGroupChatResponseDto(chatEntity.get());
    }

    public PaginatedResponseDTO<GroupChatResponseDto> getGroupChats (int pageNo, int pageSize) {
        Page<GroupChatEntity> chatEntityPage = groupChatRepository.findAll(PageRequest.of(pageNo - 1, pageSize));


        List<GroupChatResponseDto> chatsDTOs = chatEntityPage.getContent().stream()
                .map(chat -> new GroupChatResponseDto(
                        chat.getId(),
                        chat.getNameOfChat(),
                        chat.getTypeOfChat(),
                        chat.getMembers().stream()
                                .map(userMapper::fromUserEntityToUserResponseDTO)
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());

        PaginatedResponseDTO<GroupChatResponseDto> groupChatPageDTO = new PaginatedResponseDTO<>();

        groupChatPageDTO.setUsers(chatsDTOs);
        groupChatPageDTO.setPageNumber(chatEntityPage.getNumber() + 1); // +1 to adjust for zero-based pages
        groupChatPageDTO.setPageSize(chatEntityPage.getSize());
        groupChatPageDTO.setTotalElements(chatEntityPage.getTotalElements());
        groupChatPageDTO.setTotalPages(chatEntityPage.getTotalPages());

        return groupChatPageDTO;
    }
}
