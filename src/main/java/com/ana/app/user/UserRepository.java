package com.ana.app.user;

import com.ana.app.user.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByResetPasswordCode(int code);
}
