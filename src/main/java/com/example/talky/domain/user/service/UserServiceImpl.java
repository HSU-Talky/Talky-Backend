package com.example.talky.domain.user.service;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.exception.InvalidUserTypeException;
import com.example.talky.domain.auth.exception.PermissionDeniedException;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.domain.emergency_history.entity.EmergencyHistory;
import com.example.talky.domain.emergency_history.repository.EmergencyHistoryRepository;
import com.example.talky.domain.guardian.web.dto.GuardianProfileRes;
import com.example.talky.domain.user.exception.GuardianInfoRequiredException;
import com.example.talky.domain.user.exception.InvalidEmergencyTargetException;
import com.example.talky.domain.user.exception.InvalidPhoneNumberFormatException;
import com.example.talky.domain.user.exception.InvalidTtsSettingsException;
import com.example.talky.domain.user.exception.UserErrorCode;
import com.example.talky.domain.user.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final EmergencyHistoryRepository ehRepository;

    private NormalUser findNormalUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if(!(user instanceof NormalUser)){
            throw new PermissionDeniedException();
        }
        return (NormalUser) user;
    }


    @Override
    public Object getUserProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // instanceof 키워드를 사용해 역할에 맞는 DTO를 반환
        if (user instanceof NormalUser normalUser) {
            return NormalUserProfileRes.from(normalUser);
        } else if (user instanceof Guardians guardians) {
            return GuardianProfileRes.from(guardians);
        }

        throw new InvalidUserTypeException();
}
    @Transactional
    @Override
    public void updateUsername(Long userId, UsernameUpdateReq usernameUpdateReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.setUsername(usernameUpdateReq.getUsername());
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void updateIntroduction(Long userId, IntroductionUpdateReq introductionUpdateReq) {
        NormalUser normalUser = findNormalUser(userId);
        normalUser.setIntroduction(introductionUpdateReq.getIntroduction());
        userRepository.save(normalUser);
    }

    @Transactional
    @Override
    public void updateEmergencyTarget(Long userId, EmergencyTargetUpdateReq emergencyTargetUpdateReq) {
        NormalUser normalUser = findNormalUser(userId);
        
        // emergencyTarget 유효성 검증
        String target = emergencyTargetUpdateReq.getEmergencyTarget();
        
        if (!"119".equals(target) && !"guardian".equals(target)) {
            throw new InvalidEmergencyTargetException();
        }
        
        // guardian 선택 시 보호자 정보 존재 여부 확인
        if ("guardian".equals(target)) {
            if (normalUser.getGuardianPhone() == null || normalUser.getGuardianPhone().trim().isEmpty()) {
                throw new GuardianInfoRequiredException();
            }
        }
        
        normalUser.setEmergencyTarget(target);
        userRepository.save(normalUser);
    }
    @Transactional
    @Override
    public void updateTts(Long userId, TtsUpdateReq ttsUpdateReq) {
        NormalUser normalUser = findNormalUser(userId);
        
        // TTS 설정 유효성 검증
        if(ttsUpdateReq.getTtsSpeed() != null){
            if (ttsUpdateReq.getTtsSpeed() < 0.5 || ttsUpdateReq.getTtsSpeed() > 2.0) {
                throw new InvalidTtsSettingsException(UserErrorCode.INVALID_TTS_SPEED_400);
            }
            normalUser.setTtsSpeed(ttsUpdateReq.getTtsSpeed());
        }
        if(ttsUpdateReq.getTtsLanguage() != null){
            String language = ttsUpdateReq.getTtsLanguage();
            if (!"ko".equals(language) && !"en".equals(language) && !"zh".equals(language) && !"ja".equals(language)) {
                throw new InvalidTtsSettingsException(UserErrorCode.INVALID_TTS_LANGUAGE_400);
            }
            normalUser.setTtsLanguage(language);
        }
        if(ttsUpdateReq.getTtsGender() != null){
            if (!"male".equals(ttsUpdateReq.getTtsGender()) && !"female".equals(ttsUpdateReq.getTtsGender())) {
                throw new InvalidTtsSettingsException(UserErrorCode.INVALID_TTS_GENDER_400);
            }
            normalUser.setTtsGender(ttsUpdateReq.getTtsGender());
        }
        userRepository.save(normalUser);
    }

    @Transactional
    @Override
    public void updateGuardianInfo(Long userId, GuardianInfoUpdateReq guardianInfoUpdateReq) {
        NormalUser normalUser = findNormalUser(userId);
        
        // 보호자 전화번호 형식 검증 (11자리 숫자)
        String phone = guardianInfoUpdateReq.getGuardianPhone();
        if (phone != null && !phone.matches("^010\\d{8}$")) {
            throw new InvalidPhoneNumberFormatException();
        }
        
        // 독립적인 보호자 정보 업데이트
        normalUser.setGuardianName(guardianInfoUpdateReq.getGuardianName());
        normalUser.setGuardianPhone(guardianInfoUpdateReq.getGuardianPhone());
        userRepository.save(normalUser);
    }

    @Transactional
    @Override
    public GetEmergencyTarget getEmergencyTarget(Long userId, CoordinateReq request) {
        NormalUser user = findNormalUser(userId);
        if(user.isAcceptedLocationInfo()) {
            // userId와 관계를 갖는 emmergency_history에 저장
            ehRepository.save(EmergencyHistory.builder()
                            .user(user)
                            .longitude(request.getLongitude())
                            .latitude(request.getLatitude())
                            .roadAddress(request.getRoadAddress())
                    .build());
        }
        String telNum = user.getEmergencyTarget();
        return new GetEmergencyTarget(
                telNum
        );
    }

    @Transactional
    @Override
    public Void updateIsAcceptedLocationInfo(Long userId) {
        NormalUser user = findNormalUser(userId);
        log.info("변경전 값={}", user.isAcceptedLocationInfo());
        user.toggleIsAcceptedLocationInfo();
        log.info("변경후 값={}", user.isAcceptedLocationInfo());
        userRepository.save(user);
        return null;
    }
}
