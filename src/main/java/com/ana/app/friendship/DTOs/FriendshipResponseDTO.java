package com.ana.app.friendship.DTOs;

import com.ana.app.friendship.enums.FriendshipRequestResponseStatusEnum;

public class FriendshipResponseDTO {
    public FriendshipRequestResponseStatusEnum status;
    public FriendshipResponseDTO(FriendshipRequestResponseStatusEnum status){
        this.status = status;
    }
}
