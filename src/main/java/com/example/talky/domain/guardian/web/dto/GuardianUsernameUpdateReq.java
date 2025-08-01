package com.example.talky.domain.guardian.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuardianUsernameUpdateReq {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String username;
}
