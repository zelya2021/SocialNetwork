package com.ana.app.friend;

import com.ana.app.friend.DTOs.CreateFriendshipRequestDTO;
import com.ana.app.friend.DTOs.FriendshipResponseDTO;
import com.ana.app.friend.DTOs.IncomingFriendshipRequestDTO;
import com.ana.app.friend.entities.FriendshipRequestEntity;
import com.ana.app.friend.enums.FriendshipRequestResponseStatusEnum;
import com.ana.app.friend.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.login.exceptions.BadRequestException;
import com.ana.app.user.Entities.UserEntity;
import com.ana.app.user.Mappers.UserMapper;
import com.ana.app.user.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public FriendshipResponseDTO createFriendRequest(CreateFriendshipRequestDTO friendRequestDto){
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername());

        var recipient = userRepository.findById(friendRequestDto.getRecipientId());
        if(recipient.isEmpty())
            throw new BadRequestException("Recipient does not exist!");

        isFriendRequestAlreadyExist(userEntity.getId(), recipient.get().getId());

        FriendshipRequestEntity friendshipRequestEntity = new FriendshipRequestEntity();
        friendshipRequestEntity.setStatus(StatusOfFriendshipRequestEnum.PENDING);
        friendshipRequestEntity.setTimeOfReceipt(new Date());
        friendshipRequestEntity.setRecipient(recipient.get());
        friendshipRequestEntity.setSender(userEntity);

        friendshipRequestRepository.save(friendshipRequestEntity);
        return new FriendshipResponseDTO(FriendshipRequestResponseStatusEnum.FRIEND_REQUEST_CREATED);
    }

    public void isFriendRequestAlreadyExist(Long senderId, Long recipientId) {
        var friendRequestDetails = friendshipRequestRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if (friendRequestDetails.isPresent())
            throw new BadRequestException("Friend request already exist!");
    }

    public List<IncomingFriendshipRequestDTO> getIncomingFriendshipRequests(){
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userEntity = userRepository.findByEmail(userDetails.getUsername());

        var friendRequestDetails = friendshipRequestRepository.findByRecipientId(userEntity.getId());
        if (friendRequestDetails.isEmpty())
            throw new BadRequestException("You dont have friend requests!");

        return friendRequestDetails.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public IncomingFriendshipRequestDTO convertToDto(FriendshipRequestEntity entity) {
        IncomingFriendshipRequestDTO dto = new IncomingFriendshipRequestDTO();
        dto.setStatus(entity.getStatus());
        dto.setTimeOfReceipt(entity.getTimeOfReceipt());
        dto.setSender(userMapper.toUserResponseDTO(entity.getSender()));
        return dto;
    }
}
