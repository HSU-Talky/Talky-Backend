package com.example.talky.domain.practice.web.controller;

import com.example.talky.domain.practice.exception.PracticeInvalidHttpParamException;
import com.example.talky.domain.practice.service.PracticeService;
import com.example.talky.domain.practice.web.dto.GetAllRes;
import com.example.talky.global.response.ErrorResponse;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;

@Slf4j
@RestController
@RequestMapping("/prac")
@RequiredArgsConstructor
public class PracticeController {

    // 서비스 의존성 명시
    private final PracticeService practiceService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getPractice(@RequestParam Long pracId) {
        if(pracId <=0 || pracId >= 8) {
            log.info("요청 파라미터의 값이 잘못되었습니다. prac_id = {}, ", pracId);
            throw new PracticeInvalidHttpParamException();
        }
        // pracId를 서비스 계층에 던짐
        GetAllRes res = practiceService.getPractice(pracId);
        // 성공 결과 반환
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.ok(res));
    }
}
