package com.ana.app.friendship.dto;

import com.ana.app.friendship.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.user.dto.UserResponseDTO;
import lombok.Data;

import java.util.Date;

@Data
public class IncomingFriendshipRequestDTO {
    private Long id;
    private StatusOfFriendshipRequestEnum status;
    private Date timeOfReceipt;
    private UserResponseDTO sender;
}
