package com.ana.app.friend.DTOs;

import com.ana.app.friend.enums.FriendshipRequestResponseStatusEnum;

public class FriendshipResponseDTO {
    public FriendshipRequestResponseStatusEnum status;
    public FriendshipResponseDTO(FriendshipRequestResponseStatusEnum status){
        this.status = status;
    }
}
