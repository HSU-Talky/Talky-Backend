package com.example.talky.global.ai.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ToAiReq {
    private List<String> keywords;
    private String context;
    private List<String> conversations;
    private List<String> favorites;
}
