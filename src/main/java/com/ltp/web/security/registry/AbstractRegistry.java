package com.ltp.web.security.registry;

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

    void create(K key, V value){
        if(contains(key)){
            //TODO Add exception
            return;
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
