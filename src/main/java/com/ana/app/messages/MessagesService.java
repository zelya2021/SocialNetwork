package com.ana.app.messages;

import com.ana.app.messages.dto.CreateMessageDTO;
import com.ana.app.messages.dto.GetMessagesDTO;
import com.ana.app.messages.dto.MessageResponseDTO;
import com.ana.app.messages.dto.UpdateMessageDTO;

import java.util.List;

public interface MessagesService {
    MessageResponseDTO sendMessage(CreateMessageDTO createMessageDTO);
    MessageResponseDTO updateMessage(UpdateMessageDTO updateMessageDTO, Long id);
    List<MessageResponseDTO> getMessagesFromChat(Long chatId, String typeOfChat);
}
