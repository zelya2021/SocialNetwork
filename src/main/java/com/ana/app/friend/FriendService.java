package com.ana.app.friend;

import com.ana.app.friend.DTOs.CreateFriendRequestDTO;
import com.ana.app.friend.DTOs.FriendResponseDTO;

public interface FriendService {
    FriendResponseDTO createFriendRequest(CreateFriendRequestDTO friendRequest);
}
