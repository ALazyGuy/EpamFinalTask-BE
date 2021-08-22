package com.ltp.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeopleEntity extends AbstractEntity{

    private Long authorId;
    private String fullName;
    private Long cash;
    private boolean status;
    private String photoName;

    public PeopleEntity(Long authorId, String fullName, Long cash, boolean status, String photoName) {
        this(-1L, authorId, fullName, cash, status, photoName);
    }

    public PeopleEntity(Long id, Long authorId, String fullName, Long cash, boolean status, String photoName) {
        this.id = id;
        this.authorId = authorId;
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
        return (this.authorId == current.getAuthorId()) &&
                (this.fullName.equals(current.getFullName())) &&
                (this.id == current.getId()) &&
                (this.cash == current.getCash()) &&
                (this.status == current.isStatus()) &&
                (this.photoName.equals(current.getPhotoName()));
    }

    @Override
    public int hashCode(){
        int result = fullName.hashCode() + photoName.hashCode();
        result += 11 * cash + id * 5 + 161 * (status ? 0 : 1) + authorId;
        return result;
    }

    @Override
    public String toString(){
        return String.format("PeopleEntity[ID: `%d`, AuthorID: `%d`, Full Name: `%s`, Cash: `%d`, Status: `%s`, Photo Name: `%s`]",
                id,
                authorId,
                fullName,
                cash,
                (status ? "Arrested" : "Wanted"),
                photoName);
    }
}
