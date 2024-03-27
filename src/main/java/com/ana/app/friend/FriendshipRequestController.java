package com.ana.app.friend;

import com.ana.app.friend.DTOs.CreateFriendshipRequestDTO;
import com.ana.app.friend.DTOs.FriendshipResponseDTO;
import com.ana.app.friend.DTOs.IncomingFriendshipRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendshipRequestController {
    @Autowired
    private FriendshipRequestService friendshipRequestService;

    @PostMapping()
    public FriendshipResponseDTO createFriendRequest(@Valid @RequestBody CreateFriendshipRequestDTO friendRequest)
    {
        return friendshipRequestService.createFriendRequest(friendRequest);
    }

    @GetMapping()
    public List<IncomingFriendshipRequestDTO> getIncomingFriendshipRequests()
    {
        return friendshipRequestService.getIncomingFriendshipRequests();
    }
}
