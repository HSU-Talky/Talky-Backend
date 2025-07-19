package com.example.talky.domain.recommendation.entity;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.recommendation.StringListConverter;
import com.example.talky.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_id", nullable = false)
    private NormalUser normalUser;

    private String context; // 클라이언트가 직접 입력한 상황

    @Convert(converter = StringListConverter.class)
    private List<String> keywords; // 태그들

    @Convert(converter = StringListConverter.class)
    private List<String> conversations; // 이전 대화들

}
