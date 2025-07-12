package com.example.talky.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)throws ServletException, IOException {

        // 1. extractToken : Authorization 헤더에서 JWT 토큰 추출
        String token = extrackToken(request);

        // 2. validateToken : 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 3. getAuthentication : 토큰으로 사용자 인증 객체 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // 현재 요청의 SecurityContext에 인증 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);

    }

    // Authentication 헤더에서 "Bearer" 토큰 추출
    private String extrackToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
