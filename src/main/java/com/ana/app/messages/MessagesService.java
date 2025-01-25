package com.ana.app.messages;

import com.ana.app.messages.dto.*;

import java.util.List;

public interface MessagesService {
    MessageResponseDTO sendMessage(CreateMessageDTO createMessageDTO);
    MessageResponseDTO updateMessage(UpdateMessageDTO updateMessageDTO, Long id);
    List<MessageResponseDTO> getMessagesFromChat(Long chatId, String typeOfChat);
    DeleteMessageDTO deleteMessage(Long id);
}
