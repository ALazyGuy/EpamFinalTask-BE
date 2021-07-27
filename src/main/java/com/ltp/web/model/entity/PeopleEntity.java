package com.ltp.web.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class PeopleEntity implements Serializable {
    private long id;
    private String fullName;
    private long cash;
    private boolean status;
    private String photoName;

    public PeopleEntity(){
        this(-1, "", 0, false, "");
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != getClass()) return false;
        PeopleEntity people = (PeopleEntity) o;
        return people.getCash() == cash
                && people.getId() == id
                && people.getFullName().equals(fullName)
                && people.getPhotoName().equals(photoName);
    }

    @Override
    public String toString(){
        return String.format("PeopleEntity: [" +
                        "\n\tID: %d" +
                        "\n\tFull Name: %s" +
                        "\n\tCash: %d" +
                        "\n\tStatus: %b" +
                        "\n\tPhoto: %s",
                id, fullName, cash, status, photoName);
    }

    public static final class PeopleEntityFactory{
        public static PeopleEntity createPeople(String fullName,
                                              long cash,
                                              boolean status,
                                              String photoName){
            return new PeopleEntity(-1, fullName, cash, status, photoName);
        }

        private PeopleEntityFactory(){}

    }
}
