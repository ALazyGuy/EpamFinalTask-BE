package com.ltp.web.validator;

import com.ltp.web.model.dto.LoginRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthValidator {

    private static final String EMAIL_EXP = "(^\\w+@[a-zA-Z]+\\.[a-zA-Z]+$)";
    private static final String PASSWORD_EXP = "(^(([a-zA-Z0-9]|\\.|\\$|\\^)+)$)";

    public static boolean validateLogin(LoginRequest loginRequest){
        boolean isEmailValid = Pattern.matches(EMAIL_EXP, loginRequest.getEmail());
        boolean isPasswordValid = Pattern.matches(PASSWORD_EXP, loginRequest.getPassword());
        return isEmailValid && isPasswordValid && loginRequest.getPassword().length() >= 4;
    }

}
