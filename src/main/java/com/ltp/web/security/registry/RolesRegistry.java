package com.ltp.web.security.registry;

import com.ltp.web.exception.RegistryException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RolesRegistry {

    protected Map<String, String[]> registries;

    private RolesRegistry(){
        registries = new HashMap<>();
    }

    public void create(String key, String[] value) throws RegistryException {
        if(contains(key)){
            throw new RegistryException(String.format("Registry with key `%s` is already exists", key));
        }

        registries.put(key, value);
    }

    public boolean contains(String key){
        return registries.containsKey(key);
    }

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
