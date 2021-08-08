package com.ltp.web.service;

import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    boolean addUser(RegistrationRequest registrationRequest);
    Optional<UserEntity> getUserById(Long id);
    Optional<UserEntity> getUserByEmail(String email);
}
