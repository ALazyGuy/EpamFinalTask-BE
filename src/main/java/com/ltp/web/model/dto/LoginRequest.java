package com.ltp.web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class LoginRequest implements Serializable {
    private String email;
    private String password;
}
