package com.ltp.web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeopleAddRequest {
    private String fullName;
    private Long cash;
    private String photoName;
}
