package com.ltp.web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationInput {
    public String email;
    public String password;
    public String name;
    public String surname;
    public String middleName;
}
