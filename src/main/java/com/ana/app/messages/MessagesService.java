package com.ana.app.messages;

import com.ana.app.messages.dto.CreateMessageDTO;
import com.ana.app.messages.dto.MessageResponseDTO;

public interface MessagesService {
    MessageResponseDTO sendMessage(CreateMessageDTO createMessageDTO);
}
