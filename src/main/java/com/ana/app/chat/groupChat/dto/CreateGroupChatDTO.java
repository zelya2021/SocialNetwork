package com.ana.app.chat.groupChat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Set;

@Data
public class CreateGroupChatDTO {
    @Schema(description = "userIds it are the ids of the users you're planning to chat with", example = "[1,2]")
    @Valid
    @NotNull(message = "Members IDs cannot be null")
    @NotEmpty(message = "Members IDs cannot be empty")
    private Set<@Positive(message = "All member IDs must be positive") Long> membersIds ;

    private String nameOfChat;
}
