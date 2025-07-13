package com.example.talky.domain.practice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    private int nextQuestionId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
