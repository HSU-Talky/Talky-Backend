package com.example.talky.domain.user.service;

import com.example.talky.domain.user.web.dto.UsernameUpdateReq;

public interface UserService {
    // 프로필 정보 조회(normalUser or guardian)
    Object getUserProfile(Long userId);
    // 이름 수정
    void updateUsername(Long userId, UsernameUpdateReq usernameUpdateReq);

}
