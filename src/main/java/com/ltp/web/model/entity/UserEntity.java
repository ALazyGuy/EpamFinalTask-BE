package com.ltp.web.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable {

    private long id;
    private String email;
    private String name;
    private String surname;
    private String middleName;
    private String password;
    private long cash;

    public UserEntity(){
         this(-1, "", "", "", "", "");
    }

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

    @Override
    public String toString(){
        return String.format("UserEntity: [" +
                "\n\tID: %d" +
                "\n\tEmail: %s" +
                "\n\tName: %s" +
                "\n\tSurname: %s" +
                "\n\tMiddle Name: %s" +
                "\n\tPassword: %s" +
                "\n\tCash: %d\n]",
                id, email, name, surname, middleName, password, cash);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != getClass()) return false;
        UserEntity user = (UserEntity) o;
        return user.getCash() == cash
                && user.getId() == id
                && user.getEmail().equals(email)
                && user.getName().equals(name)
                && user.getSurname().equals(surname)
                && user.getMiddleName().equals(middleName)
                && user.getPassword().equals(password);
    }

    public static final class UserEntityFactory{
        public static UserEntity createUser(String email,
                               String password,
                               String name,
                               String surname,
                               String middleName){
            return new UserEntity(-1, email, name, surname, middleName, password);
        }

        private UserEntityFactory(){}

    }

}
