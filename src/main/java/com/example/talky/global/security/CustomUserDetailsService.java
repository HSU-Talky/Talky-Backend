package com.example.talky.global.security;

import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.repository.GuardianRepository;
import com.example.talky.domain.auth.repository.NormalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 모든 사용자를 조회하기 위한 구현체
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final NormalUserRepository normalUserRepository;
    private final GuardianRepository guardianRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // 먼저 일반 사용자 리포지토리에서 찾기
        User user = normalUserRepository.findByLoginId(loginId)
                .map(User.class::cast)
                .orElseGet(() ->
                    // 없으면 보호자 리포지토리에서 찾기
                    guardianRepository.findByLoginId(loginId)
                        .orElseThrow(() ->
                            new UsernameNotFoundException("해당 아이디를 가진 유저를 찾을 수 없습니다: " + loginId)
                        )
                );

        return new CustomUserDetails(user);
    }
}
