package com.example.talky.domain.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmergencyTargetUpdateReq {
    @NotBlank(message = "긴급호출 대상은 비워둘 수 없습낟.")
    private String emergencyTarget;
}
