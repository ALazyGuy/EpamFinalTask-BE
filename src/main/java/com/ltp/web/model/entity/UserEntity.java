package com.ltp.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEntity extends AbstractEntity{

    private String email;
    private String password;
    private String name;
    private String surname;
    private String middleName;
    private Long cash;

    public UserEntity(Long id, String email, String password, String name,
                      String surname, String middleName, Long cash) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.cash = cash;
    }

    public UserEntity(String email, String password, String name, String surname, String middleName, Long cash) {
        this(-1L, email, password, name, surname, middleName, cash);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != getClass()){
            return false;
        }

        UserEntity current = (UserEntity) o;
        return (this.email.equals(current.getEmail())) &&
                (this.password.equals(current.getPassword())) &&
                (this.name.equals(current.getName())) &&
                (this.surname.equals(current.getSurname())) &&
                (this.middleName.equals(current.getMiddleName())) &&
                (this.id == current.getId()) &&
                (this.cash == current.getCash());
    }

    @Override
    public int hashCode(){
        int result = email.hashCode() + password.hashCode();
        result += 3 * name.hashCode() + 7 * surname.hashCode();
        result += 11 * middleName.hashCode() + cash + id * 5;
        return result;
    }

    @Override
    public String toString(){
        return String.format("UserEntity[ID: `%d`, Email: `%s`, Password: `%s`, Name: `%s`," +
                " Surname: `%s`, Middle Name: `%s`, Cash: `%d`]",
                id, email, password, name, surname, middleName, cash);
    }

}
