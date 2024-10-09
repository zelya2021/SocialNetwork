package com.ana.app.user_chat;

import com.ana.app.chat.directChat.DirectChatService;
import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.common.dto.PaginatedResponseDTO;
import com.ana.app.user_chat.dto.ListOfChatsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User-Chats Management", description = "All types chats for user management operations")
@RestController
@RequestMapping("/user-chats")
public class UserChatsController {
    @Autowired

    private UserChatsService userChatsService;


    @Operation(summary = "Get list of chats for current log in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user")})
    @GetMapping()
    public PaginatedResponseDTO<ListOfChatsResponseDto> listOfChatsForCurrentUser(@RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "50") int limit)
    {
        return userChatsService.getlistOfChatsForCurrentUser(page,limit);
    }
}
