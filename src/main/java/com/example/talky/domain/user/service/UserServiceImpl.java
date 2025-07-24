package com.example.talky.domain.user.service;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.exception.InvalidUserTypeException;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.domain.user.web.dto.GuardianProfileRes;
import com.example.talky.domain.user.web.dto.NormalUserProfileRes;
import com.example.talky.domain.user.web.dto.UsernameUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


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
}
