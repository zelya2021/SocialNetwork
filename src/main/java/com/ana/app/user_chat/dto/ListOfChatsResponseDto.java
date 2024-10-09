package com.ana.app.user_chat.dto;

import com.ana.app.chat.directChat.dto.DirectChatResponseDto;
import com.ana.app.chat.groupChat.dto.GroupChatResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListOfChatsResponseDto {
    private Set<DirectChatResponseDto> directChats = new HashSet<>();
    private Set<GroupChatResponseDto> groupChats = new HashSet<>();
}
