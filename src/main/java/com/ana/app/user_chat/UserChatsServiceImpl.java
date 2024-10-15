package com.ana.app.user_chat;

import com.ana.app.chat.directChat.DirectChatRepository;
import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.directChat.mappers.DirectChatMapper;
import com.ana.app.chat.groupChat.GroupChatRepository;
import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.chat.groupChat.mappers.GroupChatMapper;
import com.ana.app.common.dto.PaginatedResponseDTO;
import com.ana.app.user.UserRepository;
import com.ana.app.user.entities.UserEntity;
import com.ana.app.user_chat.dto.ListOfChatsResponseDto;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserChatsServiceImpl implements UserChatsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DirectChatRepository directChatRepository;
    @Autowired
    private GroupChatRepository groupChatRepository;
    private static final DirectChatMapper directChatMapper = Mappers.getMapper(DirectChatMapper.class);
    private static final GroupChatMapper groupChatMapper = Mappers.getMapper(GroupChatMapper.class);


    public PaginatedResponseDTO<ListOfChatsResponseDto> getlistOfChatsForCurrentUser(int pageNo, int pageSize){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity currentUserEntity = userRepository.findByEmail(userDetails.getUsername());

        Page<DirectChatEntity> directChatPage = directChatRepository.findAllByUser(PageRequest.of(pageNo - 1, pageSize), currentUserEntity);
        Page<GroupChatEntity> groupChatPage = groupChatRepository.findAllByMembers(PageRequest.of(pageNo - 1, pageSize), currentUserEntity);

        Set<DirectChatResponseDto> directChatDtos = directChatPage.getContent().stream()
                .map(directChatMapper::fromDirectChatEntityToDirectChatResponseDTO)
                .collect(Collectors.toSet());

        Set<GroupChatResponseDto> groupChatDtos = groupChatPage.getContent().stream()
                .map(groupChatMapper::fromGroupChatEntityToGroupChatResponseDto)
                .collect(Collectors.toSet());

        ListOfChatsResponseDto responseDto = new ListOfChatsResponseDto();
        responseDto.setDirectChats(directChatDtos);
        responseDto.setGroupChats(groupChatDtos);

        PaginatedResponseDTO<ListOfChatsResponseDto> paginatedResponseDTO = new PaginatedResponseDTO<>();
        paginatedResponseDTO.setUsers(Arrays.asList(responseDto));
        paginatedResponseDTO.setPageNumber(directChatPage.getNumber() + 1);  // Adjust for zero-based pages
        paginatedResponseDTO.setPageSize(directChatPage.getSize());
        paginatedResponseDTO.setTotalElements(directChatPage.getTotalElements() + groupChatPage.getTotalElements());
        paginatedResponseDTO.setTotalPages(Math.max(directChatPage.getTotalPages(), groupChatPage.getTotalPages()));  // Use max to combine page numbers

        return paginatedResponseDTO;
    }
}
