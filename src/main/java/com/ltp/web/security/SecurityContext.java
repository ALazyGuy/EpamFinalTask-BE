package com.ltp.web.security;

import com.ltp.web.model.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContext {

    private UserEntity current;

    public void authenticate(UserEntity userEntity){
        current = userEntity;
    }

    public Optional<UserEntity> getAuthenticated(){
        return (current == null ? Optional.empty() : Optional.of(current));
    }

    public static SecurityContext getInstance(){
        return SecurityContextHolder.INSTANCE;
    }

    private static final class SecurityContextHolder{
        public static final SecurityContext INSTANCE = new SecurityContext();
    }

}
