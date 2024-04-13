package com.ana.app.friendship;

import com.ana.app.friendship.DTOs.CreateFriendshipRequestDTO;
import com.ana.app.friendship.DTOs.FriendshipResponseDTO;
import com.ana.app.user.DTOs.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Friendship Management", description = "Friendship management operations")
@RestController
@RequestMapping("/friendship-request")
public class FriendshipRequestController {
    @Autowired
    private FriendshipRequestService friendshipRequestService;

    @Operation(summary = "Create Friendship request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Recipient does not exist!") })
    @PostMapping()
    public FriendshipResponseDTO createFriendRequest(@Valid @RequestBody CreateFriendshipRequestDTO friendRequest)
    {
        return friendshipRequestService.createFriendRequest(friendRequest);
    }

    @Operation(summary = "Get incoming Friendship request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Current user does not have friend requests!") })
    @GetMapping("/incoming")
    public List<FriendshipResponseDTO> getIncomingFriendshipRequests()
    {
        return friendshipRequestService.getIncomingFriendshipRequests();
    }

    @Operation(summary = "Get friends request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user") })
    @GetMapping("/friends")
    public List<UserResponseDTO> getFriends()
    {
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return friendshipRequestService.getFriends(userDetails.getUsername());
    }

    @Operation(summary = "Accept Friendship request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Friendship Request does not exist") })
    @PostMapping("/{id}/accept")
    public FriendshipResponseDTO acceptFriendshipRequests(@PathVariable("id") Long id)
    {
        return friendshipRequestService.acceptFriendshipRequests(id);
    }

    @Operation(summary = "Decline Friendship request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Friendship Request does not exist") })
    @PostMapping("/{id}/decline")
    public FriendshipResponseDTO declineFriendshipRequests(@PathVariable("id") Long id)
    {
        return friendshipRequestService.declineFriendshipRequests(id);
    }
}
