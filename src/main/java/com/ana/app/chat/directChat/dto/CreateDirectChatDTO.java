package com.ana.app.chat.directChat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateDirectChatDTO {
    @Schema(description = "userId it is the id of the user you're planning to chat with", example = "1")
    @Valid
    @NotNull(message = "Members IDs cannot be null")
    @NotEmpty(message = "Members IDs cannot be empty")
    private @Positive(message = "Id must be positive") Long userId;
}
