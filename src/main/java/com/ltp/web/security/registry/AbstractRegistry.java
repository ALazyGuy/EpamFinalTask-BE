package com.ltp.web.security.registry;

import com.ltp.web.exception.RegistryException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractRegistry <K, V>{

    private Map<K, V[]> registries;

    public AbstractRegistry(){
        registries = new HashMap<>();
    }

    public void create(K key, V[] value) throws RegistryException {
        if(contains(key)){
            throw new RegistryException(String.format("Registry with key `%s` is already exists", key.toString()));
        }

        registries.put(key, value);
    }

    public boolean contains(K key){
        return registries.containsKey(key);
    }

    public abstract boolean validate(K key, V value);
}
