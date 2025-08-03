package com.example.talky.domain.guardian.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConnectUserReq {
    @NotBlank(message = "연결 코드는 필수입니다.")
    private String connectionCode;
}
