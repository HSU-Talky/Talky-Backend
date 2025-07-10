package com.example.talky.domain.auth.service;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.exception.DuplicateLoginIdException;
import com.example.talky.domain.auth.exception.InvalidUserTypeException;
import com.example.talky.domain.auth.repository.GuardianRepository;
import com.example.talky.domain.auth.repository.NormalUserRepository;
import com.example.talky.domain.auth.web.dto.SignUpReq;
import com.example.talky.domain.auth.web.dto.SignUpRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final GuardianRepository guardianRepository;
    private final NormalUserRepository normalUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
                "guardian",
                null // 보호자는 연결 코드가 없음
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
        String connectionCode = generateConnectionCode();
        normalUser.setConnectionCode(connectionCode);
        
        NormalUser savedUser = normalUserRepository.save(normalUser);
        
        return new SignUpRes(
                savedUser.getId(),
                "normal",
                connectionCode
        );
    }

    // 6자리 랜덤 숫자로 구성된 연결 코드 생성
    private String generateConnectionCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
