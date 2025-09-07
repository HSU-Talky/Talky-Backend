package com.example.talky.adapter.ai.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AiRcmdRes {
    private String category;
    private List<RcmdSentence> RecommendedSentences;

    static class RcmdSentence {
        private Long id;
        private String text;
    }

    public AiRcmdRes(
            String category,
            List<RcmdSentence> recommended_sentences
    ) {
        this.category = category;
        this.RecommendedSentences = recommended_sentences;
    }
}
