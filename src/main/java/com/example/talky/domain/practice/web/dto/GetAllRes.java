package com.example.talky.domain.practice.web.dto;

import com.example.talky.domain.practice.domain.Question;

import java.util.List;

public record GetAllRes(
        List<Question> all
) {
    public record AllQuestion(
            Long id,
            String question,
            List<AnswerSet> answers
    ) {
        public record AnswerSet(
                String answer,
                Integer nextQuestionId
        ) {}
    }
}
