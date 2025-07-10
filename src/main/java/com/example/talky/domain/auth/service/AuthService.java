package com.example.talky.domain.auth.service;

import com.example.talky.domain.auth.web.dto.SignUpReq;
import com.example.talky.domain.auth.web.dto.SignUpRes;

public interface AuthService {

    SignUpRes signUp(SignUpReq signUpReq);
    SignUpRes createGuardianAccount(SignUpReq signUpReq);
    SignUpRes createNormalUserAccount(SignUpReq signUpReq);

    }