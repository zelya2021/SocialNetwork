package com.ana.app.friend.DTOs;

import com.ana.app.friend.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.user.DTOs.UserResponseDTO;
import lombok.Data;

import java.util.Date;

@Data
public class IncomingFriendshipRequestDTO {
    private StatusOfFriendshipRequestEnum status;
    private Date timeOfReceipt;
    private UserResponseDTO sender;
}
