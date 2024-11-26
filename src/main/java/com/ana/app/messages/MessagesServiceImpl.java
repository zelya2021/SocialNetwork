package com.ana.app.messages;

import com.ana.app.auth.exceptions.BadRequestException;
import com.ana.app.chat.directChat.DirectChatRepository;
import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.chat.groupChat.GroupChatRepository;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.messages.dto.CreateMessageDTO;
import com.ana.app.messages.dto.GetMessagesDTO;
import com.ana.app.messages.dto.MessageResponseDTO;
import com.ana.app.messages.dto.UpdateMessageDTO;
import com.ana.app.messages.entities.MessageEntity;
import com.ana.app.messages.mappers.MessageMapper;
import com.ana.app.user.UserRepository;
import com.ana.app.user.entities.UserEntity;
import com.ana.app.user.mappers.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DirectChatRepository directChatRepository;
    @Autowired
    private GroupChatRepository groupChatRepository;
    @Autowired
    private MessageRepository messageRepository;
    private static final MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);
    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public MessageResponseDTO sendMessage(CreateMessageDTO createMessageDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity currentUserEntity = userRepository.findByEmail(userDetails.getUsername());

        MessageEntity messageEntity = new MessageEntity();
        if (createMessageDTO.getTypeOfChat() == TypeOfChat.DIRECT) {
            Optional<DirectChatEntity> IsChatEntity = directChatRepository.findById(createMessageDTO.getChatId());
            if (IsChatEntity.isEmpty())
                throw new BadRequestException("Direct chat with this id does not exist!");
            var directChatEntity = IsChatEntity.get();

            messageEntity.setDirectChat(directChatEntity);
        } else {
            Optional<GroupChatEntity> IsChatEntity = groupChatRepository.findById(createMessageDTO.getChatId());
            if (IsChatEntity.isEmpty())
                throw new BadRequestException("Group chat with this id does not exist!");
            var groupChatEntity = IsChatEntity.get();

            messageEntity.setGroupChat(groupChatEntity);
        }
        messageEntity.setTime(LocalDateTime.now());
        messageEntity.setAuthor(currentUserEntity);
        messageEntity.setMessage(createMessageDTO.getMessage());

        messageRepository.save(messageEntity);

        MessageResponseDTO responseDTO = new MessageResponseDTO();
        responseDTO.setId(messageEntity.getId());
        responseDTO.setMessage(messageEntity.getMessage());
        responseDTO.setChatId(createMessageDTO.getChatId());
        responseDTO.setTypeOfChat(createMessageDTO.getTypeOfChat());
        responseDTO.setTime(messageEntity.getTime());
        responseDTO.setAuthor(userMapper.fromUserEntityToUserResponseDTO(currentUserEntity));
        return responseDTO;
    }

    public MessageResponseDTO updateMessage(UpdateMessageDTO updateMessageDTO, Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity currentUserEntity = userRepository.findByEmail(userDetails.getUsername());
        Optional<MessageEntity> optionalMessageEntity = messageRepository.findById(id);

        if (optionalMessageEntity.isEmpty()) {
            throw new BadRequestException("Message with this id does not exist!");
        }
        var messageEntity = optionalMessageEntity.get();

        if (updateMessageDTO.getTypeOfChat() == TypeOfChat.DIRECT) {
            Optional<DirectChatEntity> IsChatEntity = directChatRepository.findById(updateMessageDTO.getChatId());
            if (IsChatEntity.isEmpty())
                throw new BadRequestException("Direct chat with this id does not exist!");
        } else {
            Optional<GroupChatEntity> IsChatEntity = groupChatRepository.findById(updateMessageDTO.getChatId());
            if (IsChatEntity.isEmpty())
                throw new BadRequestException("Group chat with this id does not exist!");
        }

        if (!messageRepository.existsByIdAndAuthorId(messageEntity.getId(), currentUserEntity.getId()))
            throw new BadRequestException("Message doesn`t belong to the current user!!");

        messageEntity.setMessage(updateMessageDTO.getMessage());
        messageRepository.save(messageEntity);

        MessageResponseDTO responseDTO = new MessageResponseDTO();
        responseDTO.setId(messageEntity.getId());
        responseDTO.setMessage(messageEntity.getMessage());
        responseDTO.setChatId(updateMessageDTO.getChatId());
        responseDTO.setTypeOfChat(updateMessageDTO.getTypeOfChat());
        responseDTO.setTime(messageEntity.getTime());
        responseDTO.setAuthor(userMapper.fromUserEntityToUserResponseDTO(currentUserEntity));
        return responseDTO;
    }

    public List<MessageResponseDTO> getMessagesFromChat(Long chatId, String typeOfChat) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity currentUserEntity = userRepository.findByEmail(userDetails.getUsername());

        if(TypeOfChat.DIRECT.name().equals(typeOfChat)){
            DirectChatEntity directChat = directChatRepository.findById(chatId)
                    .orElseThrow(() -> new BadRequestException("Direct chat not found"));

            // Check if current user is part of the chat
            if (!isUserParticipantInDirectChat(directChat, currentUserEntity.getId())) {
                throw new BadRequestException("User not authorized to view these messages");
            }
            return messageRepository.findByDirectChat(directChat).stream()
                    .map(message -> {
                        MessageResponseDTO dto = messageMapper.fromMessageEntityToMessageResponseDTO(message);
                        dto.setTypeOfChat(TypeOfChat.valueOf(typeOfChat));
                        dto.setChatId(chatId);
                        return dto;
                    }).collect(Collectors.toList());

        } else if (TypeOfChat.GROUP.name().equals(typeOfChat)) {
            GroupChatEntity groupChat = groupChatRepository.findById(chatId)
                    .orElseThrow(() -> new BadRequestException("Group chat not found"));

            // Check if current user is part of the chat
            if (!isUserMemberOfGroupChat(groupChat, currentUserEntity.getId())) {
                throw new BadRequestException("User not authorized to view these messages");
            }

            return messageRepository.findByGroupChat(groupChat).stream()
                    .map(message -> {
                        MessageResponseDTO dto = messageMapper.fromMessageEntityToMessageResponseDTO(message);
                        dto.setTypeOfChat(TypeOfChat.valueOf(typeOfChat));
                        dto.setChatId(chatId);
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        else throw new BadRequestException("typeOfChat should equal to DIRECT or GROUP");
    }

    private boolean isUserParticipantInDirectChat(DirectChatEntity directChat, Long userId) {
        return Objects.equals(directChat.getUser1().getId(), userId) || Objects.equals(directChat.getUser2().getId(), userId);
    }

    private boolean isUserMemberOfGroupChat(GroupChatEntity groupChat, Long userId) {
        return groupChat.getMembers().stream()
                .anyMatch(user -> Objects.equals(user.getId(), userId));
    }
}
