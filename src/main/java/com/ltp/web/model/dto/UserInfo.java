package com.ltp.web.model.dto;

import com.ltp.web.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfo {
    private String email;
    private String name;
    private String surname;
    private String middleName;
    private Long cash;
    private String role;

    public UserInfo(UserEntity userEntity){
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.surname = userEntity.getSurname();
        this.middleName = userEntity.getMiddleName();
        this.cash = userEntity.getCash();
        this.role = userEntity.getRole().name();
    }
}
