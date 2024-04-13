package com.ana.app.friendship;

import com.ana.app.friendship.DTOs.CreateFriendshipRequestDTO;
import com.ana.app.friendship.DTOs.FriendshipResponseDTO;
import com.ana.app.friendship.DTOs.IncomingFriendshipRequestDTO;
import com.ana.app.user.DTOs.UserResponseDTO;

import java.util.List;

public interface FriendshipRequestService {
    FriendshipResponseDTO createFriendRequest(CreateFriendshipRequestDTO friendRequest);
    List<FriendshipResponseDTO> getIncomingFriendshipRequests();
    FriendshipResponseDTO acceptFriendshipRequests(Long id);
    FriendshipResponseDTO declineFriendshipRequests(Long id);
    List<UserResponseDTO> getFriends(String email);
}
