package com.ana.app.friendship;

import com.ana.app.friendship.DTOs.CreateFriendshipRequestDTO;
import com.ana.app.friendship.DTOs.FriendshipResponseDTO;
import com.ana.app.friendship.DTOs.IncomingFriendshipRequestDTO;

import java.util.List;

public interface FriendshipRequestService {
    FriendshipResponseDTO createFriendRequest(CreateFriendshipRequestDTO friendRequest);
    List<IncomingFriendshipRequestDTO> getIncomingFriendshipRequests();
    FriendshipResponseDTO acceptFriendshipRequests(Long id);
    FriendshipResponseDTO declineFriendshipRequests(Long id);
}
