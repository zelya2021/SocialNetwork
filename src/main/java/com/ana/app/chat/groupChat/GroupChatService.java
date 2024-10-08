package com.ana.app.chat.groupChat;

import com.ana.app.chat.DeleteChatDTO;
import com.ana.app.chat.groupChat.dto.CreateGroupChatDTO;
import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import com.ana.app.common.dto.PaginatedResponseDTO;

public interface GroupChatService {
    GroupChatResponseDto createGroupChat(CreateGroupChatDTO groupChatDTO);
    GroupChatResponseDto updateGroupChat(Long id, CreateGroupChatDTO updateChatDTO);
    DeleteChatDTO deleteGroupChat(Long id);
    GroupChatResponseDto getGroupChat(Long id);
    PaginatedResponseDTO<GroupChatResponseDto> getGroupChats (int pageNo, int pageSize);
}
