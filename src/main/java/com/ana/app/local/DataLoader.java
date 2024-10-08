package com.ana.app.local;

import com.ana.app.chat.directChat.DirectChatRepository;
import com.ana.app.chat.directChat.entities.DirectChatEntity;
import com.ana.app.chat.enums.TypeOfChat;
import com.ana.app.chat.groupChat.GroupChatRepository;
import com.ana.app.chat.groupChat.entities.GroupChatEntity;
import com.ana.app.friendship.FriendshipRequestRepository;
import com.ana.app.friendship.entities.FriendshipRequestEntity;
import com.ana.app.friendship.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.user.entities.UserEntity;
import com.ana.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoader {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRequestRepository friendRequestRepository;
    @Autowired
    private DirectChatRepository directChatRepository;
    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner loadData(UserRepository userRepository, FriendshipRequestRepository friendRequestRepository) {
        return args -> {
            UserEntity user1 = new UserEntity(1, "Anna", "Zelo", "anastasiia.zelonkina@gmail.com",  passwordEncoder.encode("12345678"));
            UserEntity user2 = new UserEntity(2, "Jane", "Doe", "jane.doe@example.com", passwordEncoder.encode("12345678"));
            UserEntity user3 = new UserEntity(3, "Alan", "Morgan", "alan.doe@example.com", passwordEncoder.encode("12345678"));
            UserEntity user4 = new UserEntity(4, "Mila", "Razy", "mila.doe@example.com", passwordEncoder.encode("12345678"));

            Set<UserEntity> friendsOfUser4 = new HashSet<>();
            friendsOfUser4.add(user3);
            user4.setFriends(friendsOfUser4);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            FriendshipRequestEntity friendRequest = new FriendshipRequestEntity(1, StatusOfFriendshipRequestEnum.PENDING, new Date(), user1, user2);
            FriendshipRequestEntity friendRequest1 = new FriendshipRequestEntity(2, StatusOfFriendshipRequestEnum.PENDING, new Date(), user1, user3);

            friendRequestRepository.save(friendRequest);
            friendRequestRepository.save(friendRequest1);

            DirectChatEntity directChatEntity1 = new DirectChatEntity(1,TypeOfChat.DIRECT, user1, user2);
            DirectChatEntity directChatEntity2 = new DirectChatEntity(2,TypeOfChat.DIRECT, user1, user3);
            DirectChatEntity directChatEntity3 = new DirectChatEntity(3, TypeOfChat.DIRECT, user2, user3);
            directChatRepository.save(directChatEntity1);
            directChatRepository.save(directChatEntity2);
            directChatRepository.save(directChatEntity3);

            Set<UserEntity> group_chat1 = new HashSet<>();
            group_chat1.add(user1);
            group_chat1.add(user2);
            group_chat1.add(user3);

            GroupChatEntity groupChatEntity1 = new GroupChatEntity(1,"BFF", TypeOfChat.GROUP , group_chat1);
            groupChatRepository.save(groupChatEntity1);
        };
    }
}
