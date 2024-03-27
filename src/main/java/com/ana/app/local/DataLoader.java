package com.ana.app.local;

import com.ana.app.friend.FriendshipRequestRepository;
import com.ana.app.friend.entities.FriendshipRequestEntity;
import com.ana.app.friend.enums.StatusOfFriendshipRequestEnum;
import com.ana.app.user.Entities.UserEntity;
import com.ana.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Configuration
public class DataLoader {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRequestRepository friendRequestRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner loadData(UserRepository userRepository, FriendshipRequestRepository friendRequestRepository) {
        return args -> {
            UserEntity user1 = new UserEntity(1, "Anna", "Zelo", "anastasiia.zelonkina@gmail.com",  passwordEncoder.encode("12345678"));
            UserEntity user2 = new UserEntity(2, "Jane", "Doe", "jane.doe@example.com", passwordEncoder.encode("12345678"));
            UserEntity user3 = new UserEntity(3, "Alan", "Morgan", "alan.doe@example.com", passwordEncoder.encode("12345678"));

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            FriendshipRequestEntity friendRequest = new FriendshipRequestEntity(1, StatusOfFriendshipRequestEnum.PENDING, new Date(), user1, user2);
            FriendshipRequestEntity friendRequest1 = new FriendshipRequestEntity(2, StatusOfFriendshipRequestEnum.PENDING, new Date(), user1, user3);

            friendRequestRepository.save(friendRequest);
            friendRequestRepository.save(friendRequest1);
        };
    }
}
