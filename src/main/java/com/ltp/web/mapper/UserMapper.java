package com.ltp.web.mapper;

import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.model.entity.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserEntity mapToUser(RegistrationRequest registrationRequest){
        return new UserEntity(registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                registrationRequest.getName(),
                registrationRequest.getSurname(),
                registrationRequest.getMiddleName(), 0L, UserRole.ROLE_USER);
    }

}
