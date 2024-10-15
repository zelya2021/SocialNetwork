package com.ana.app.messages;

import com.ana.app.messages.dto.CreateMessageDTO;
import com.ana.app.messages.dto.MessageResponseDTO;
import com.ana.app.messages.dto.UpdateMessageDTO;

public interface MessagesService {
    MessageResponseDTO sendMessage(CreateMessageDTO createMessageDTO);
    MessageResponseDTO updateMessage(UpdateMessageDTO updateMessageDTO, Long id);
}
