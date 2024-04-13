package com.ana.app.friendship.DTOs;

import com.ana.app.friendship.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.user.DTOs.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipResponseDTO {
    public Long id;
    public Date timeOfReceipt;
    public UserResponseDTO sender;
    public UserResponseDTO recipient;
    public StatusOfFriendshipRequestEnum status;
}
