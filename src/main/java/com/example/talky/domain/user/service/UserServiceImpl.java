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
    }
    @Transactional
    @Override
    public void updateIntroduction(Long userId, IntroductionUpdateReq introductionUpdateReq) {
        NormalUser normalUser = findNormalUser(userId);
        normalUser.setIntroduction(introductionUpdateReq.getIntroduction());


    }

    @Transactional
    @Override
    public void updateEmergencyTarget(Long userId, EmergencyTargetUpdateReq emergencyTargetUpdateReq) {
        NormalUser normalUser = findNormalUser(userId);
        normalUser.setEmergencyTarget(emergencyTargetUpdateReq.getEmergencyTarget());
    }
    @Transactional
    @Override
    public void updateTts(Long userId, TtsUpdateReq ttsUpdateReq) {
        NormalUser normalUser = findNormalUser(userId);
        if(ttsUpdateReq.getTtsSpeed() != null){
            normalUser.setTtsSpeed(ttsUpdateReq.getTtsSpeed());
        }
        if(ttsUpdateReq.getTtsLanguage() != null){
            normalUser.setTtsLanguage(ttsUpdateReq.getTtsLanguage());
        }
        if(ttsUpdateReq.getTtsGender() != null){
            normalUser.setTtsGender(ttsUpdateReq.getTtsGender());
        }
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
        return null;
    }
}
