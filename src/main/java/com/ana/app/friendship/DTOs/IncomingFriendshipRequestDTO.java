package com.ana.app.friendship.DTOs;

import com.ana.app.friendship.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.user.DTOs.UserResponseDTO;
import lombok.Data;

import java.util.Date;

@Data
public class IncomingFriendshipRequestDTO {
    private Long id;
    private StatusOfFriendshipRequestEnum status;
    private Date timeOfReceipt;
    private UserResponseDTO sender;
}
