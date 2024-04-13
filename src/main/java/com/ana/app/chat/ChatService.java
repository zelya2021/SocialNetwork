package com.ana.app.chat;

import com.ana.app.chat.DTOs.ChatResponseDTO;
import com.ana.app.chat.DTOs.CreateDirectChatDTO;

public interface ChatService {
    ChatResponseDTO createDirectChat(CreateDirectChatDTO directChatDTO);
}
