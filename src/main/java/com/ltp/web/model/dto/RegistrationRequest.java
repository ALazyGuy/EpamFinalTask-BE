package com.ltp.web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationRequest implements Serializable {
    public String email;
    public String password;
    public String name;
    public String surname;
    public String middleName;
}
