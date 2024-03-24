package com.ana.app.friend;

import com.ana.app.friend.DTOs.CreateFriendRequestDTO;
import com.ana.app.friend.DTOs.FriendResponseDTO;
import com.ana.app.user.DTOs.CreateUserDTO;
import com.ana.app.user.DTOs.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @PostMapping()
    public FriendResponseDTO createFriendRequest(@Valid @RequestBody CreateFriendRequestDTO friendRequest)
    {
        return friendService.createFriendRequest(friendRequest);
    }
}
