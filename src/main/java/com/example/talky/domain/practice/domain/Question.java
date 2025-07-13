package com.example.talky.domain.practice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sentence;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
}
