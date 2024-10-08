package com.ana.app.friendship;

import com.ana.app.friendship.dto.CreateFriendshipRequestDTO;
import com.ana.app.friendship.dto.FriendshipResponseDTO;
import com.ana.app.user.dto.UserResponseDTO;

import java.util.List;

public interface FriendshipRequestService {
    FriendshipResponseDTO createFriendRequest(CreateFriendshipRequestDTO friendRequest);
    List<FriendshipResponseDTO> getIncomingFriendshipRequests();
    FriendshipResponseDTO acceptFriendshipRequests(Long id);
    FriendshipResponseDTO declineFriendshipRequests(Long id);
    List<UserResponseDTO> getFriends(String email);
}
