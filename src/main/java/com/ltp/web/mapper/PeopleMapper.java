package com.ltp.web.mapper;

import com.ltp.web.model.dto.PeopleAddRequest;
import com.ltp.web.model.entity.PeopleEntity;
import com.ltp.web.security.SecurityContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeopleMapper {

    public static PeopleEntity mapToPeople(PeopleAddRequest peopleAddRequest){
        Long authorId = SecurityContext
                .getInstance()
                .getAuthenticated()
                .get()
                .getId();
        return new PeopleEntity(authorId,
                peopleAddRequest.getFullName(),
                peopleAddRequest.getCash(),
                false,
                peopleAddRequest.getPhotoName());
    }

}
