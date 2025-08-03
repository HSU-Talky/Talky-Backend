package com.example.talky.domain.recommendation.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GetContextReq {
    @NotEmpty(message = "키워드는 필수 입력값입니다.")
    private List<String> keywords;

    private String context;
    private String choose;
    private List<String> conversations;
}
