package com.ltp.web.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class UserEntity {

    @Getter(AccessLevel.NONE)
    private long id;
    private String email;
    private String name;
    private String surname;
    private String middleName;
    private String password;
    private long cash;

    private UserEntity(long id, String email, String name, String surname,
                      String middleName, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.password = password;
        this.cash = 0;
    }

    public static final class UserEntityFactory{
        public static UserEntity createUser(String email,
                               String password,
                               String name,
                               String surname,
                               String middleName){
            return new UserEntity(0, email, name, surname, middleName, password);
        }

        private UserEntityFactory(){}

    }

}
