package com.ana.app.messages;

import com.ana.app.messages.dto.CreateMessageDTO;
import com.ana.app.messages.dto.MessageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        var msg = messagesService.sendMessage(createMessageDTO);
        System.out.println(msg);
        return msg;
    }
}
