package com.example.talky.domain.favorites.web.controller;

import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    @GetMapping("/user")
    public ResponseEntity<SuccessResponse<?>> findFavorite(Object tmp) {
        // 1. JWT 정보와 RequestBody를 서비스계층에 위임
        // 2. return ResponseEntity
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse<?>> addFavorite(Object tmp) {
        // 1. JWT 인증 | DTO -> Entity를 서비스계층에 위임
        // 2. return ResponseEntity
        return null;
    }

    @DeleteMapping("/")
    public ResponseEntity<SuccessResponse<?>> deleteFavorite(Object tmp) {
        // 1. JWT를 통해 User 인증하고, 서비스 계층에 위임
        // 2. return ResponseEntity
        return null;
    }
}
