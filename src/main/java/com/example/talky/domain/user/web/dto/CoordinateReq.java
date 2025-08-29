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
    @NotAccepted(message = "위치정보 접근 동의한 사용자에 한해 긴급호출 사용 시 도로명주소 데이터를 전달하여야 합니다.")
    private String roadAddress;
}
