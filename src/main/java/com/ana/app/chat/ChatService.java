package com.ana.app.chat;

import com.ana.app.chat.DTOs.ChatResponseDTO;
import com.ana.app.chat.DTOs.CreateDirectChatDTO;
import com.ana.app.chat.DTOs.CreateGroupChatDTO;
import com.ana.app.chat.DTOs.DeleteChatDTO;
import com.ana.app.common.DTOs.PaginatedResponseDTO;

public interface ChatService {
    ChatResponseDTO createDirectChat(CreateDirectChatDTO directChatDTO);
    ChatResponseDTO createGroupChat(CreateGroupChatDTO groupChatDTO);
    ChatResponseDTO updateGroupChat(Long id, CreateGroupChatDTO updateChatDTO);
    DeleteChatDTO deleteChat(Long id);
    ChatResponseDTO getChat(Long id);
    PaginatedResponseDTO<ChatResponseDTO> getChats (int pageNo, int pageSize);
}
