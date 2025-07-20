package com.example.talky.global.security;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.favorites.exception.ForbiddenFavoriteException;
import com.example.talky.global.response.code.ErrorResponseCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 현재 인증된 사용자가 NormalUser 타입인지 검증하고 반환하는 메소드
    // 지금은 안쓰이지만 추후 @AuthenticationPrinciple로 로그인  사용자정보 받아올 수 있음(Controller에서)
    public NormalUser getNormalUser() {
        if (!(user instanceof NormalUser)) {
            throw new UserNotFoundException();
        }
        return (NormalUser) user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
    }
}
