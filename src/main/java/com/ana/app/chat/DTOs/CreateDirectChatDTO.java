package com.ana.app.chat.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateDirectChatDTO {
    @Schema(description = "userId it is the id of the user you're planning to chat with", example = "1")
    private Long userId;
}
