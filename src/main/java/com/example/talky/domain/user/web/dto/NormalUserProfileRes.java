package com.example.talky.domain.user.web.dto;

import com.example.talky.domain.auth.entity.NormalUser;

public record NormalUserProfileRes(
        String userType,
        String username,
        String loginId,
        String introduction,
        String connectionCode,
        String emergencyTarget,
        TtsSettings ttsSettings,
        GuardianInfo guardianInfo
) {
    public static NormalUserProfileRes from(NormalUser user) {
        // emergencyTarget은 @PrePersist에서 기본값 "119"로 설정됨
        String emergencyTarget = user.getEmergencyTarget();
        
        // TTS 설정을 별도 객체로 분리
        TtsSettings ttsSettings = new TtsSettings(
                user.getTtsSpeed(),
                user.getTtsLanguage(),
                user.getTtsGender()
        );
        
        // 보호자 정보 (독립적으로 저장된 보호자 정보)
        GuardianInfo guardianInfo = null;
        if (user.getGuardianName() != null && user.getGuardianPhone() != null) {
            guardianInfo = new GuardianInfo(
                    user.getGuardianName(),
                    user.getGuardianPhone()
            );
        }
        
        return new NormalUserProfileRes(
                "normal",
                user.getUsername(),
                user.getLoginId(),
                user.getIntroduction(),
                user.getConnectionCode(),
                emergencyTarget,
                ttsSettings,
                guardianInfo
        );
    }
    
    // TTS 설정을 위한 내부 클래스
    public record TtsSettings(
            Double ttsSpeed,
            String ttsLanguage,
            String ttsGender
    ) {}
    
    // 보호자 정보를 위한 내부 클래스
    public record GuardianInfo(
            String guardianName,
            String guardianPhone
    ) {}
}
