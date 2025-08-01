package com.example.talky.domain.guardian.web.dto;ㅎ

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationAlertUpdateReq {
    @NotNull(message = "위치 알림 설정은 필수입니다.")
    private Boolean locationEnabled;
}
