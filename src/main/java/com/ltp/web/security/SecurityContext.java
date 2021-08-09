package com.ltp.web.security;

import com.ltp.web.exception.RegistryException;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.security.registry.RolesRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContext {

    private UserEntity current;
    private boolean authenticated;

    public void authenticate(UserEntity userEntity){
        current = userEntity;
        authenticated = true;
    }

    public void deauthenticate(){
        authenticated = false;
        current = null;
    }

    public Optional<UserEntity> getAuthenticated(){
        return (current == null ? Optional.empty() : Optional.of(current));
    }

    public boolean tryGetAccess(String mapping) throws RegistryException {
        String role = (authenticated ? current.getRole().name() : "SHARED");
        return RolesRegistry.getInstance().validate(role, mapping);
    }

    public static SecurityContext getInstance(){
        return SecurityContextHolder.INSTANCE;
    }

    private static final class SecurityContextHolder{
        public static final SecurityContext INSTANCE = new SecurityContext();
    }

}
