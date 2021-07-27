package com.ltp.web.mapper;

import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.model.entity.UserEntity;

public class RegistrationToUserMapper {

    public UserEntity map(RegistrationRequest registrationRequest){
        return UserEntity.UserEntityFactory.createUser(registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                registrationRequest.getName(),
                registrationRequest.getSurname(),
                registrationRequest.getMiddleName());
    }

}
