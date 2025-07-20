package com.example.talky.domain.user.service;

public interface UserService {
    // 프로필 정보 조회(normalUser or guardian)
    Object getUserProfile(Long userId);

}
