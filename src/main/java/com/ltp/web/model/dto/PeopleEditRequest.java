package com.ltp.web.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeopleEditRequest {
    private Long peopleId;
    private String fullName;
    private Long cash;
}
