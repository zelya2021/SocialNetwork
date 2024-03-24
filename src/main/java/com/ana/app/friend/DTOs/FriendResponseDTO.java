package com.ana.app.friend.DTOs;

import com.ana.app.friend.enums.FriendResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FriendResponseDTO {
    public FriendResponseStatusEnum status;
    public FriendResponseDTO(FriendResponseStatusEnum status){
        this.status = status;
    }
}
