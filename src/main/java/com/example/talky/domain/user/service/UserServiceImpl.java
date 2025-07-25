package com.example.talky.domain.user.service;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.exception.InvalidUserTypeException;
import com.example.talky.domain.auth.exception.PermissionDeniedException;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.domain.user.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

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


}
