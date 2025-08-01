package com.example.talky.domain.recommendation.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class GetContextReq {
    @NotNull(message = "키워드는 필수 입력값입니다.")
    private List<String> keywords;
    private String context;
    private String choose;
    private List<String> conversations;
}
