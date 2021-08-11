package com.ltp.web.mapper;

import com.ltp.web.model.dto.PeopleAddRequest;
import com.ltp.web.model.entity.PeopleEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeopleMapper {

    public static PeopleEntity mapToPeople(PeopleAddRequest peopleAddRequest){
        return new PeopleEntity(peopleAddRequest.getFullName(),
                peopleAddRequest.getCash(),
                false,
                peopleAddRequest.getPhotoName());
    }

}
