package com.ana.app.chat;

import com.ana.app.chat.DTOs.ChatResponseDTO;
import com.ana.app.chat.DTOs.CreateDirectChatDTO;
import com.ana.app.chat.DTOs.CreateGroupChatDTO;
import com.ana.app.chat.DTOs.DeleteChatDTO;
import com.ana.app.common.DTOs.PaginatedResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("direct-chat")
    public ChatResponseDTO createDirectChat(@RequestBody CreateDirectChatDTO directChatDTO)
    {
        return chatService.createDirectChat(directChatDTO);
    }

    @Operation(summary = "Create group chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User/users with provided ids does not found!")})
    @PostMapping("group-chat")
    public ChatResponseDTO createGroupChat(@RequestBody @Valid CreateGroupChatDTO groupChatDTO)
    {
        return chatService.createGroupChat(groupChatDTO);
    }

    @Operation(summary = "Update group chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User/users with provided ids does not found!"),
            @ApiResponse(responseCode = "400", description = "Chat with this id does not exist!")})
    @PostMapping("group/{id}")
    public ChatResponseDTO updateGroupChat(@PathVariable("id") Long id, @RequestBody @Valid CreateGroupChatDTO updateChatDTO)
    {
        return chatService.updateGroupChat(id, updateChatDTO);
    }

    @Operation(summary = "Delete chats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Chat with this id does not exist!")})
    @DeleteMapping("{id}")
    public DeleteChatDTO deleteDirectChat(@PathVariable("id") Long id)
    {
        return chatService.deleteChat(id);
    }

    @Operation(summary = "Get chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Chat with this id does not exist!")})
    @GetMapping("{id}")
    public ChatResponseDTO getChat(@PathVariable("id") Long id)
    {
        return chatService.getChat(id);
    }

    @Operation(summary = "Get chats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user")})
    @GetMapping()
    public PaginatedResponseDTO<ChatResponseDTO> getChats(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "50") int limit)
    {
        return chatService.getChats(page,limit);
    }
}
