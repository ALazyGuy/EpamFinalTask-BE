package com.ltp.web.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private final PasswordEncoder passwordEncoder;

    private PasswordUtil(){
        this.passwordEncoder = new BCryptPasswordEncoder(10);
    }

    public String encode(String password){
        return passwordEncoder.encode(password);
    }

    public boolean validate(String password, String hash){
        return passwordEncoder.matches(password, hash);
    }

    public static PasswordUtil getInstance(){
        return PasswordUtilHolder.INSTANCE;
    }

    private static final class PasswordUtilHolder{
        public static final PasswordUtil INSTANCE = new PasswordUtil();
    }

}
