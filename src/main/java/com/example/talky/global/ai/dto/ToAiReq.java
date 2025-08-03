package com.example.talky.global.ai.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ToAiReq {
    private List<String> keywords;
    private String context;
    private List<String> conversations;
    private List<String> favorites;
}
