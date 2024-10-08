package com.ana.app.chat.directChat;

import com.ana.app.chat.DeleteChatDTO;
import com.ana.app.chat.directChat.dto.CreateDirectChatDTO;
import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.common.dto.PaginatedResponseDTO;

public interface DirectChatService {
    DirectChatResponseDto createDirectChat(CreateDirectChatDTO directChatDTO);
    DeleteChatDTO deleteDirectChat(Long id);
    DirectChatResponseDto getDirectChat(Long id);
    PaginatedResponseDTO<DirectChatResponseDto> getDirectChats (int pageNo, int pageSize);
}
