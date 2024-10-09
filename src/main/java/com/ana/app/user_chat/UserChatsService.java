package com.ana.app.user_chat;

import com.ana.app.common.dto.PaginatedResponseDTO;
import com.ana.app.user_chat.dto.ListOfChatsResponseDto;

public interface UserChatsService {
    PaginatedResponseDTO<ListOfChatsResponseDto> getlistOfChatsForCurrentUser(int pageNo, int pageSize);
}
