package com.example.talky.domain.practice.web.dto;

import java.util.List;

public record GetAllRes(
        List<QuestionSet> question
        // List<AnswerSet> answers
) {
        public record QuestionSet(
                Long id,
                String content,
                List<AnswerSet> answers
        ) {
                public record AnswerSet(
                        String answer,
                        Integer nextQuestionId
                ) {}
        }
}

