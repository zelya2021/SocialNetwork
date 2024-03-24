package com.ana.app.friend;

import com.ana.app.friend.DTOs.CreateFriendRequestDTO;
import com.ana.app.friend.DTOs.FriendResponseDTO;
import com.ana.app.friend.entities.FriendRequestEntity;
import com.ana.app.friend.enums.FriendResponseStatusEnum;
import com.ana.app.friend.enums.StatusOfFriendRequestEnum;
import com.ana.app.login.exceptions.BadRequestException;
import com.ana.app.user.Entities.UserEntity;
import com.ana.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FriendServiceImpl implements FriendService{
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public FriendResponseDTO createFriendRequest(CreateFriendRequestDTO friendRequestDto){
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername());

        var recipient = userRepository.findById(friendRequestDto.getRecipientId());
        if(recipient.isEmpty())
            throw new BadRequestException("Recipient does not exist!");

        isFriendRequestAlreadyExist(userEntity.getId(), recipient.get().getId());

        FriendRequestEntity friendRequestEntity = new FriendRequestEntity();
        friendRequestEntity.setStatus(StatusOfFriendRequestEnum.PENDING);
        friendRequestEntity.setTimeOfReceipt(new Date());
        friendRequestEntity.setRecipient(recipient.get());
        friendRequestEntity.setSender(userEntity);

        friendRepository.save(friendRequestEntity);
        return new FriendResponseDTO(FriendResponseStatusEnum.FRIEND_REQUEST_CREATED);
    }

    public void isFriendRequestAlreadyExist(Long senderId, Long recipientId) {
        var friendRequestDetails = friendRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if (friendRequestDetails.isPresent())
            throw new BadRequestException("Friend request already exist!");
    }
}
