package com.ana.app.user;

import com.ana.app.user.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByResetPasswordCode(int code);
    Set<UserEntity> findByIdIn (Set<Long> ids);
}
