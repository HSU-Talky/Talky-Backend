package com.example.talky.domain.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoordinateReq {
    private String longitude;
    private String latitude;
}
