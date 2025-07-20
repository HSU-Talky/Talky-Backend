package com.example.talky.domain.user.web.dto;

import com.example.talky.domain.auth.entity.NormalUser;

public record NormalUserProfileRes(
        String userType,
        String username,
        String loginId,
        String introduction,
        String connectionCode,
        String emergencyTarget,
        Double ttsSpeed,
        String ttsLanguage,
        String ttsGender
) {
    public static NormalUserProfileRes from(NormalUser user) {
        return new NormalUserProfileRes(
                "normal",
                user.getUsername(),
                user.getLoginId(),
                user.getIntroduction(),
                user.getConnectionCode(),
                user.getEmergencyTarget(),
                user.getTtsSpeed(),
                user.getTtsLanguage(),
                user.getTtsGender()
        );
    }
}
