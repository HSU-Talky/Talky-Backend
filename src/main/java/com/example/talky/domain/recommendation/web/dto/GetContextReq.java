package com.example.talky.domain.recommendation.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetContextReq {
    private List<String> keywords;
    private String context;
    private String choose;
    private String sttMessage; // 사용자가 음성으로 입력한 mp3 파일을 서버에서 텍스트로 변환(STT)한 결과값. 추천 문장 생성에 활용됨.
}
