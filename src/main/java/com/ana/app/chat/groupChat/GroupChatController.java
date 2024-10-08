package com.ana.app.chat.groupChat;

import com.ana.app.chat.DeleteChatDTO;
import com.ana.app.chat.groupChat.dto.CreateGroupChatDTO;
import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import com.ana.app.common.dto.PaginatedResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Group Chat Management", description = "Group chat management operations")
@RestController
@RequestMapping("/group-chat")
public class GroupChatController {
    @Autowired
    private GroupChatService groupChatService;

    @Operation(summary = "Create group chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User/users with provided ids does not found!")})
    @PostMapping()
    public GroupChatResponseDto createGroupChat(@RequestBody CreateGroupChatDTO groupChatDTO)
    {
        return groupChatService.createGroupChat(groupChatDTO);
    }

    @Operation(summary = "Update group chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User/users with provided ids does not found!")})
    @PostMapping("update")
    public GroupChatResponseDto updateGroupChat(@RequestBody CreateGroupChatDTO groupChatDTO, Long id)
    {
        return groupChatService.updateGroupChat(id, groupChatDTO);
    }

    @Operation(summary = "Delete group chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Chat with this id does not exist!")})
    @DeleteMapping("{id}")
    public DeleteChatDTO deleteGroupChat(@PathVariable("id") Long id)
    {
        return groupChatService.deleteGroupChat(id);
    }

    @Operation(summary = "Get group chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Group chat with this id does not exist!")})
    @GetMapping("{id}")
    public GroupChatResponseDto getGroupChat(@PathVariable("id") Long id)
    {
        return groupChatService.getGroupChat(id);
    }

    @Operation(summary = "Get group chats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user")})
    @GetMapping()
    public PaginatedResponseDTO<GroupChatResponseDto> getChats(@RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "50") int limit)
    {
        return groupChatService.getGroupChats(page,limit);
    }
}
