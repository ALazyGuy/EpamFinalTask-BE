package com.ltp.web.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeopleEditRequest {
    private Long id;
    private String fullName;
    private Long cash;
}
