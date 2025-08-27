package com.example.talky.domain.user.web.dto;

import com.example.talky.domain.user.web.dto.validate.NotAccepted;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoordinateReq {
    @NotAccepted
    private String longitude;
    @NotAccepted
    private String latitude;
}
