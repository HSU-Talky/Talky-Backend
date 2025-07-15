package com.example.talky.global.ai.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ToAiReq {
    private List<String> keywords;
    private String context;
    private List<String> conversations;
    private List<String> favorites;
}
