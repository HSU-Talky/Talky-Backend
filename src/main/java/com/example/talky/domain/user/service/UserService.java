package com.example.talky.domain.user.service;

import com.example.talky.domain.user.web.dto.EmergencyTargetUpdateReq;
import com.example.talky.domain.user.web.dto.IntroductionUpdateReq;
import com.example.talky.domain.user.web.dto.TtsUpdateReq;
import com.example.talky.domain.user.web.dto.UsernameUpdateReq;

public interface UserService {
    // 프로필 정보 조회(normalUser or guardian)
    Object getUserProfile(Long userId);
    // 이름 수정
    void updateUsername(Long userId, UsernameUpdateReq usernameUpdateReq);
    // 자기소개 수정
    void updateIntroduction(Long userId, IntroductionUpdateReq introductionUpdateReq);
    // 긴급 전화 타겟 수정
    void updateEmergencyTarget(Long userId, EmergencyTargetUpdateReq emergencyTargetUpdateReq);
    // tts 수정
    void updateTts(Long userId, TtsUpdateReq ttsUpdateReq);

}
