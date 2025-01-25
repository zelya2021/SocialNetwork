package com.ana.app.messages;

import com.ana.app.messages.dto.CreateMessageDTO;
import com.ana.app.messages.dto.DeleteMessageDTO;
import com.ana.app.messages.dto.MessageResponseDTO;
import com.ana.app.messages.dto.UpdateMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Message Management", description = "Message management operations")
@RestController
@RequestMapping("/message")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    @Operation(summary = "Create message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Direct chat with this id does not exist!"),
            @ApiResponse(responseCode = "400", description = "Group chat with this id does not exist!")})
    @PostMapping()
    public MessageResponseDTO createGroupChat(@RequestBody CreateMessageDTO createMessageDTO)
    {
       return messagesService.sendMessage(createMessageDTO);
    }

    @Operation(summary = "Update message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "User/users with provided ids does not found!")})
    @PostMapping("/{id}")
    public MessageResponseDTO updateMessage(@PathVariable("id") Long id, @RequestBody UpdateMessageDTO updateMessageDTO)
    {
        return messagesService.updateMessage(updateMessageDTO, id);
    }

    @Operation(summary = "Get messages from direct or group chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Direct chat not found!"),
            @ApiResponse(responseCode = "400", description = "User not authorized to view these messages!")})
    @GetMapping()
    public List<MessageResponseDTO> getMessages(@RequestParam(value = "chatId", required = true) Long chatId,
                                                @RequestParam(value = "typeOfChat", required = true) String typeOfChat)
    {
        return messagesService.getMessagesFromChat(chatId, typeOfChat);
    }

    @Operation(summary = "Delete message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Non authorized user"),
            @ApiResponse(responseCode = "400", description = "Message not found!")})
    @DeleteMapping("{id}")
    public DeleteMessageDTO deleteMessage(@PathVariable("id") Long id)
    {
        return messagesService.deleteMessage(id);
    }
}
