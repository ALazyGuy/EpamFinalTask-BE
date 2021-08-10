package com.ltp.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeopleEntity extends AbstractEntity{

    private String fullName;
    private Long cash;
    private boolean status;
    private String photoName;

    public PeopleEntity(String fullName, Long cash, boolean status, String photoName) {
        this(-1L, fullName, cash, status, photoName);
    }

    public PeopleEntity(Long id, String fullName, Long cash, boolean status, String photoName) {
        this.id = id;
        this.fullName = fullName;
        this.cash = cash;
        this.status = status;
        this.photoName = photoName;
    }

    public boolean equals(Object o){
        if(o == null || o.getClass() != getClass()){
            return false;
        }

        PeopleEntity current = (PeopleEntity) o;
        return (this.fullName.equals(current.getFullName())) &&
                (this.id == current.getId()) &&
                (this.cash == current.getCash()) &&
                (this.photoName == current.getPhotoName());
    }

    @Override
    public int hashCode(){
        int result = fullName.hashCode() + photoName.hashCode();
        result += 11 * cash + id * 5 + 161 * (status ? 0 : 1);
        return result;
    }

    @Override
    public String toString(){
        return String.format("PeopleEntity[ID: `%d`, Full Name: `%s`, Cash: `%d`, Status: `%s`, Photo Name: `%s`]",
                id,
                fullName,
                cash,
                (status ? "Arrested" : "Wanted"),
                photoName);
    }
}
