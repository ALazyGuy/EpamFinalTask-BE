package com.ltp.web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class RegistrationRequest implements Serializable {
    public String email;
    public String password;
    public String name;
    public String surname;
    public String middleName;
}
