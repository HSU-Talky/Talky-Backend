package com.example.talky.domain.recommendation.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GetContextReq {
    @NotEmpty(message = "장소는 필수 전달값입니다.")
    private String place;

    @NotEmpty(message = "키워드는 필수 입력값입니다.")
    private List<String> keywords;

    private String context;
    private String choose;
    private String sttMessage;
}
