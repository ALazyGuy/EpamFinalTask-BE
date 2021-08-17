package com.ltp.web.validator;

import com.ltp.web.model.dto.LoginRequest;
import com.ltp.web.model.dto.RegistrationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthValidator {

    private static final String EMAIL_EXP = "(^\\w+@[a-zA-Z]+\\.[a-zA-Z]+$)";
    private static final String PASSWORD_EXP = "(^((\\w|\\.|\\$|\\^){4,30})$)";
    private static final String FULL_NAME_EXP = "(^[a-zA-Z]{2,20}$)";

    public static boolean validateLogin(LoginRequest loginRequest){
        boolean isEmailValid = Pattern.matches(EMAIL_EXP, loginRequest.getEmail());
        boolean isPasswordValid = Pattern.matches(PASSWORD_EXP, loginRequest.getPassword());
        return isEmailValid && isPasswordValid && loginRequest.getPassword().length() >= 4;
    }

    public static boolean validateRegistration(RegistrationRequest registrationRequest){
        boolean isEmailValid = Pattern.matches(EMAIL_EXP, registrationRequest.getEmail());
        boolean isPasswordValid = Pattern.matches(PASSWORD_EXP, registrationRequest.getPassword());
        boolean isFullNameValid = Pattern.matches(FULL_NAME_EXP, registrationRequest.getName())
                && Pattern.matches(FULL_NAME_EXP, registrationRequest.getSurname())
                && Pattern.matches(FULL_NAME_EXP, registrationRequest.getMiddleName());
        return isEmailValid
                && isPasswordValid
                && isFullNameValid
                && registrationRequest.getPassword().length() >= 4;
    }

}
