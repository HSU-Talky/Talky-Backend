package com.example.talky.domain.auth.service;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.global.util.UserUtils;
import com.example.talky.domain.auth.exception.DuplicateLoginIdException;
import com.example.talky.domain.auth.exception.InvalidUserTypeException;
import com.example.talky.domain.auth.repository.GuardianRepository;
import com.example.talky.domain.auth.repository.NormalUserRepository;
import com.example.talky.domain.auth.web.dto.LoginReq;
import com.example.talky.domain.auth.web.dto.LoginRes;
import com.example.talky.domain.auth.web.dto.SignUpReq;
import com.example.talky.domain.auth.web.dto.SignUpRes;
import com.example.talky.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final GuardianRepository guardianRepository;
    private final NormalUserRepository normalUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserUtils userUtils;


    @Override
    @Transactional
    public SignUpRes signUp(SignUpReq signUpReq) {
        // 아이디 중복 확인
        if (guardianRepository.existsByLoginId(signUpReq.getLoginId()) || 
            normalUserRepository.existsByLoginId(signUpReq.getLoginId())) {
            throw new DuplicateLoginIdException();
        }

        // 사용자 유형에 따라 계정 생성
        if ("guardian".equalsIgnoreCase(signUpReq.getUserType())) {
            return createGuardianAccount(signUpReq);
        } else if ("normal".equalsIgnoreCase(signUpReq.getUserType())) {
            return createNormalUserAccount(signUpReq);
        } else {
            throw new InvalidUserTypeException();
        }
    }
    @Override
    @Transactional
    public SignUpRes createGuardianAccount(SignUpReq signUpReq) {
        Guardians guardian = Guardians.builder()
                .loginId(signUpReq.getLoginId())
                .password(bCryptPasswordEncoder.encode(signUpReq.getPassword()))
                .username(signUpReq.getUsername())
                .locationEnabled(false)
                .build();
        
        Guardians savedGuardian = guardianRepository.save(guardian);
        
        return new SignUpRes(
                savedGuardian.getId(),
                "guardian"
        );
    }
    @Override
    @Transactional
    public SignUpRes createNormalUserAccount(SignUpReq signUpReq) {
        NormalUser normalUser = NormalUser.builder()
                .loginId(signUpReq.getLoginId())
                .password(bCryptPasswordEncoder.encode(signUpReq.getPassword()))
                .username(signUpReq.getUsername())
                .build();
        
        // 연결 코드 생성 (6자리 랜덤 숫자)
        String connectionCode = userUtils.generateConnectionCode();
        normalUser.setConnectionCode(connectionCode);
        
        NormalUser savedUser = normalUserRepository.save(normalUser);
        
        return new SignUpRes(
                savedUser.getId(),
                "normal"

        );
    }



    @Override
    @Transactional(readOnly = true)
    public LoginRes login(LoginReq loginReq) {
        // 사용자 찾기
        User user = userUtils.findUserByLoginId(loginReq.getLoginId())
                .orElseThrow(UserNotFoundException::new);

        // 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new InvalidUserTypeException();
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user);

        // 사용자 유형 확인 및 응답 생성
        String userType = userUtils.getUserType(user);

        return new LoginRes(
                user.getId(),
                userType,
                token,
                user.getUsername()
        );
    }





}
