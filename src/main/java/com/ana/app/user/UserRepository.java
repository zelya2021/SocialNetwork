package com.ana.app.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByResetPasswordCode(int code);
}
