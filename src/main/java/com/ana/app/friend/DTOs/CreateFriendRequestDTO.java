package com.ana.app.friend.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFriendRequestDTO {
    private Long recipientId;
}
