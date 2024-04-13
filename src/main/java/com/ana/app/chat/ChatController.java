package com.ana.app.chat;

import com.ana.app.chat.DTOs.ChatResponseDTO;
import com.ana.app.chat.DTOs.CreateDirectChatDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chat Management", description = "Chat management operations")
@RestController
@RequestMapping("/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Operation(summary = "Create direct chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User with provided id not found!"),
            @ApiResponse(responseCode = "400", description = "Chat with this user id already exist!")})
    @PostMapping()
    public ChatResponseDTO createDirectChat(@RequestBody CreateDirectChatDTO directChatDTO)
    {
        return chatService.createDirectChat(directChatDTO);
    }
}
