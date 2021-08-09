package com.ltp.web.security.registry;

import com.ltp.web.exception.RegistryException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RolesRegistry extends AbstractRegistry<String, String>{

    @Override
    public boolean validate(String key, String value) throws RegistryException {
        if(!contains(key)){
            throw new RegistryException(String.format("No registry found with key `%s`", key));
        }

        return Arrays.stream(registries.get(key)).filter(a -> a.equals(value)).count() > 0;
    }

    public static RolesRegistry getInstance(){
        return RolesRegistryHolder.INSTANCE;
    }

    private static final class RolesRegistryHolder{
        public static final RolesRegistry INSTANCE = new RolesRegistry();
    }
}
