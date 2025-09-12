package com.example.talky.domain.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuardianInfoUpdateReq {
    @NotBlank(message = "보호자 이름은 필수 입력 값입니다.")
    private String guardianName;
    
    @NotBlank(message = "보호자 연락처는 필수 입력 값입니다.")
    private String guardianPhone;
}
