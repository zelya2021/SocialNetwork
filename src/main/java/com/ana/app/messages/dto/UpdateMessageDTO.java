package com.ana.app.messages.dto;
import com.ana.app.chat.enums.TypeOfChat;
import lombok.Data;

@Data
public class UpdateMessageDTO {
    private TypeOfChat typeOfChat;
    private long chatId;
    private String message;
}
