package com.example.talky.adapter.ai.response;

import com.example.talky.adapter.ai.dto.Sentence;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendationResponse {
    private String category;
    private List<Sentence> recommended_sentences;
}

