package com.ltp.web.security.registry;

import com.ltp.web.exception.RegistryException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class AbstractRegistry <K, V>{

    private Map<K, V> registries;
    private Predicate<V> condition;

    public AbstractRegistry(Predicate<V> condition){
        registries = new HashMap<>();
        this.condition = condition;
    }

    void create(K key, V value) throws RegistryException {
        if(contains(key)){
            throw new RegistryException(String.format("Registry with key `%s` is already exists", key.toString()));
        }

        registries.put(key, value);
    }

    boolean contains(K key){
        return registries.containsKey(key);
    }

    boolean validate(K key, V value){
        if(!contains(key)){
            return false;
        }

        return condition.test(registries.get(key));
    }
}
