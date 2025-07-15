package com.example.talky.domain.auth.service;

import com.example.talky.domain.auth.web.dto.LoginReq;
import com.example.talky.domain.auth.web.dto.LoginRes;
import com.example.talky.domain.auth.web.dto.SignUpReq;
import com.example.talky.domain.auth.web.dto.SignUpRes;

public interface AuthService {

    SignUpRes signUp(SignUpReq signUpReq);
    SignUpRes createGuardianAccount(SignUpReq signUpReq);
    SignUpRes createNormalUserAccount(SignUpReq signUpReq);
    LoginRes login(LoginReq loginReq);
    // 아이디 중복 확인
    void checkIdAvailability(String loginId);

    }