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
}
