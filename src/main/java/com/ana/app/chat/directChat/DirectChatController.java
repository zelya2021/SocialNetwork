package com.ana.app.chat.directChat;

import com.ana.app.chat.DeleteChatDTO;
import com.ana.app.chat.directChat.dto.CreateDirectChatDTO;
import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.common.dto.PaginatedResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Direct Chat Management", description = "Direct chat management operations")
@RestController
@RequestMapping("/direct-chat")
public class DirectChatController {
    @Autowired
    private DirectChatService directChatService;

    @Operation(summary = "Create direct chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User with provided id not found!"),
            @ApiResponse(responseCode = "400", description = "Chat with this user id already exist!")})
    @PostMapping()
    public DirectChatResponseDto createDirectChat(@RequestBody CreateDirectChatDTO directChatDTO)
    {
        return directChatService.createDirectChat(directChatDTO);
    }

    @Operation(summary = "Delete direct chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Chat with this id does not exist!")})
    @DeleteMapping("{id}")
    public DeleteChatDTO deleteDirectChat(@PathVariable("id") Long id)
    {
        return directChatService.deleteDirectChat(id);
    }

    @Operation(summary = "Get direct chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Direct chat with this id does not exist!")})
    @GetMapping("{id}")
    public DirectChatResponseDto getChat(@PathVariable("id") Long id)
    {
        return directChatService.getDirectChat(id);
    }

    @Operation(summary = "Get direct chats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user")})
    @GetMapping()
    public PaginatedResponseDTO<DirectChatResponseDto> getChats(@RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "50") int limit)
    {
        return directChatService.getDirectChats(page,limit);
    }
}
