package com.example.talky.global.jwt;

import com.example.talky.domain.auth.entity.User;
import com.example.talky.global.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration; // 밀리초 단위

    private Key key;

    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 토큰 생성
    public String createToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(user.getLoginId())
                .claim("loginId", user.getLoginId())
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Claims 파싱
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // SecurityContext에 들어갈 인증 객체를 만드는 핵심 메소드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String loginId = claims.getSubject();

        // 1. DB에서 유저 조회
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);

        // 2. CustomUserDetails 생성

        // 3. 인증 객체 생성 후 반환
        // principal: 사용자 정보 객체
        // credentials: 인증 수단 (보통 비밀번호)
        // authorities: 권한 목록
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

    }

}
