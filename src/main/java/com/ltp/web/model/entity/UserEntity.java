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
    private UserRole role;

    public UserEntity(Long id, String email, String password, String name,
                      String surname, String middleName, Long cash, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.cash = cash;
        this.role = role;
    }

    public UserEntity(String email, String password, String name, String surname, String middleName, Long cash, UserRole role) {
        this(-1L, email, password, name, surname, middleName, cash, role);
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
                (this.cash == current.getCash()) &&
                (this.role == current.getRole());
    }

    @Override
    public int hashCode(){
        int result = email.hashCode() + password.hashCode();
        result += 3 * name.hashCode() + 7 * surname.hashCode();
        result += 11 * middleName.hashCode() + cash + id * 5 + 161 * (role == UserRole.ROLE_USER ? 0 : 1);
        return result;
    }

    @Override
    public String toString(){
        return String.format("UserEntity[ID: `%d`, Email: `%s`, Password: `%s`, Name: `%s`," +
                " Surname: `%s`, Middle Name: `%s`, Cash: `%d`, Role: `%s`]",
                id, email, password, name, surname, middleName, cash, role.name());
    }

}
