package com.ana.app.messages.dto;

import com.ana.app.chat.enums.TypeOfChat;
import lombok.Data;

@Data
public class GetMessagesDTO {
    private long chatId;
}
