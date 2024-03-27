package com.ana.app.friend;

import com.ana.app.friend.DTOs.CreateFriendshipRequestDTO;
import com.ana.app.friend.DTOs.FriendshipResponseDTO;
import com.ana.app.friend.DTOs.IncomingFriendshipRequestDTO;

import java.util.List;

public interface FriendshipRequestService {
    FriendshipResponseDTO createFriendRequest(CreateFriendshipRequestDTO friendRequest);
    List<IncomingFriendshipRequestDTO> getIncomingFriendshipRequests();
}
