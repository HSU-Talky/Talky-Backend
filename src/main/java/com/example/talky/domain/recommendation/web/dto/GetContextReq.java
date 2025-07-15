package com.example.talky.domain.recommendation.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetContextReq {
    private List<String> keywords;
    private String context;
    private String choose;
    private List<String> conversations;
}
