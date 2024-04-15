package com.ana.app.chat.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class CreateGroupChatDTO {
    @Schema(description = "userIds it are the ids of the users you're planning to chat with", example = "[1,2]")
    private Set<Long> membersIds;

    private String nameOfChat;
}
