package com.example.talky.domain.favorites.web.controller;

import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.favorites.service.FavoriteService;
import com.example.talky.domain.favorites.web.dto.AllFavoriteRes;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteReq;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteRes;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/")
    public ResponseEntity<SuccessResponse<?>> getAllFavorite() {
        // 1. JWT 인증 | DTO -> Entity를 서비스계층에 위임
        AllFavoriteRes res;
        try {
            res = favoriteService.getAllFavorite(1L);
        } catch (RuntimeException e) {
            log.info("UserNotFoundException : {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
        // 2. return ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.ok(res));
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createFavorite(
            @RequestBody @Validated CreateFavoriteReq req) {

        // 1. JWT 정보와 RequestBody를 서비스계층에 위임
        CreateFavoriteRes res;
        try {
            res = favoriteService.create(1L, req);
        } catch (RuntimeException e) {
            /**
             * UserNotFoundException이 없으므로
             * 서비스계층에서 normal_user = null일 시,
             * RuntimeError를 컨트롤러로 던짐. 여기서 catch
             * FIXME
             */
            log.info("UserNotFoundException : {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }

        // 2. return ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.created(res));
    }

    @DeleteMapping("/")
    public ResponseEntity<SuccessResponse<?>> deleteFavorite(Object tmp) {
        // 1. JWT를 통해 User 인증하고, 서비스 계층에 위임
        // 2. return ResponseEntity
        return null;
    }
}
