package com.ana.app.chat;

import com.ana.app.chat.DTOs.ChatResponseDTO;
import com.ana.app.chat.DTOs.CreateDirectChatDTO;
import com.ana.app.chat.DTOs.CreateGroupChatDTO;

public interface ChatService {
    ChatResponseDTO createDirectChat(CreateDirectChatDTO directChatDTO);
    ChatResponseDTO createGroupChat(CreateGroupChatDTO groupChatDTO);
}
