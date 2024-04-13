package com.ana.app.friendship;

import com.ana.app.friendship.DTOs.CreateFriendshipRequestDTO;
import com.ana.app.friendship.DTOs.FriendshipResponseDTO;
import com.ana.app.friendship.entities.FriendshipRequestEntity;
import com.ana.app.friendship.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.auth.exceptions.BadRequestException;
import com.ana.app.mapper.FriendshipMapper;
import com.ana.app.user.DTOs.UserResponseDTO;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static final FriendshipMapper friendshipMapper = Mappers.getMapper(FriendshipMapper.class);

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

        return friendshipMapper.friendshipRequestEntityToFriendshipResponseDTO(friendshipRequestEntity);
    }

    public void isFriendRequestAlreadyExist(Long senderId, Long recipientId) {
        var friendRequestDetails = friendshipRequestRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if (friendRequestDetails.isPresent())
            throw new BadRequestException("Friend request already exist!");
    }

    public List<FriendshipResponseDTO> getIncomingFriendshipRequests(){
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userEntity = userRepository.findByEmail(userDetails.getUsername());

        var friendRequestDetails = friendshipRequestRepository.findByRecipientId(userEntity.getId());
        if (friendRequestDetails.isEmpty())
            throw new BadRequestException("You dont have friend requests!");

        return friendRequestDetails.stream()
                .filter(friendRequest -> StatusOfFriendshipRequestEnum.PENDING.equals(friendRequest.getStatus()))
                .map(friendshipMapper::friendshipRequestEntityToFriendshipResponseDTO)
                .collect(Collectors.toList());
    }

    public FriendshipResponseDTO acceptFriendshipRequests(Long id){
        var friendRequestDetails = friendshipRequestRepository.getFriendshipRequestEntityById(id);
        if (friendRequestDetails.isEmpty())
            throw new BadRequestException("Friend Request does not exist!");

        var friendshipEntity = friendRequestDetails.get();
        friendshipEntity.setStatus(StatusOfFriendshipRequestEnum.ACCEPTED);

        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userEntity = userRepository.findByEmail(userDetails.getUsername());

        Set<UserEntity> currentFriends = userEntity.getFriends();
        currentFriends.add(friendshipEntity.getSender());
        userEntity.setFriends(currentFriends);
        userRepository.save(userEntity);

        friendshipRequestRepository.save(friendshipEntity);
        return friendshipMapper.friendshipRequestEntityToFriendshipResponseDTO(friendshipEntity);
    }

    public FriendshipResponseDTO declineFriendshipRequests(Long id){
        var friendRequestDetails = friendshipRequestRepository.getFriendshipRequestEntityById(id);
        if (friendRequestDetails.isEmpty())
            throw new BadRequestException("Friend Request does not exist!");

        var friendshipEntity = friendRequestDetails.get();
        friendshipEntity.setStatus(StatusOfFriendshipRequestEnum.DECLINED);
        friendshipRequestRepository.save(friendshipEntity);

        return friendshipMapper.friendshipRequestEntityToFriendshipResponseDTO(friendshipEntity);
    }

    public List<UserResponseDTO> getFriends(String email){
        var userEntity = userRepository.findByEmail(email);

        return userEntity.getFriends()
                .stream()
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }
}
