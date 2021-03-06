package com.ltp.web.service.impl;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.mapper.UserMapper;
import com.ltp.web.model.dto.LoginRequest;
import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.model.entity.PeopleEntity;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.repository.UserRepository;
import com.ltp.web.security.SecurityContext;
import com.ltp.web.service.UserService;
import com.ltp.web.util.PasswordUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public boolean addUser(RegistrationRequest registrationRequest) {
        try {
            if(UserRepository.getInstance().getByEmail(registrationRequest.getEmail()).isPresent()){
                return false;
            }

            UserEntity userEntity = UserMapper.mapToUser(registrationRequest);
            userEntity.setPassword(PasswordUtil.getInstance().encode(userEntity.getPassword()));
            UserRepository.getInstance().save(userEntity);
            return true;
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error(String.format("Error while creating user: {%s}", e.getMessage()));
        }

        return false;
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        try {
            return UserRepository.getInstance().getById(id);
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error(String.format("Error while loading user with {ID: %d} user: {%s}", id, e.getMessage()));
        }

        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        try {
            return UserRepository.getInstance().getByEmail(email);
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error(String.format("Error while loading user with {Email: %s} user: {%s}", email, e.getMessage()));
        }

        return Optional.empty();
    }

    @Override
    public boolean authenticate(LoginRequest loginRequest) {
        return authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @Override
    public boolean authenticate(String email) {
        Optional<UserEntity> user = getUserByEmail(email);
        if(user.isEmpty()){
            return false;
        }

        SecurityContext.getInstance().authenticate(user.get());

        return true;
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<UserEntity> user = getUserByEmail(email);
        if(user.isEmpty() || !PasswordUtil.getInstance().validate(password, user.get().getPassword())){
            return false;
        }

        SecurityContext.getInstance().authenticate(user.get());

        return true;
    }

    @Override
    public boolean addCash(Long peopleId) {
        UserEntity current = null;

        try {
            Optional<UserEntity> currentOpt = SecurityContext.getInstance().getAuthenticated();
            if (currentOpt.isEmpty()) {
                return false;
            }

            PeopleEntity peopleEntity = PeopleServiceImpl.getInstance().getById(peopleId).get();

            current = currentOpt.get();
            current.setCash(current.getCash() + peopleEntity.getCash());

            UserRepository.getInstance().save(current);
        }catch(ConnectionPoolException | SQLException e){
            LOGGER.error(String.format("Error while adding cash to user %s", current.toString()));
            return false;
        }

        return true;
    }

    public static UserServiceImpl getInstance(){
        return UserServiceImplHolder.INSTANCE;
    }

    private static final class UserServiceImplHolder{
        public static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }
}
