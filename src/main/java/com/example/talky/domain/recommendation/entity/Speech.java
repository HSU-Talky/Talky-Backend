package com.example.talky.domain.recommendation.entity;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Speech extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_id", nullable = false)
    private NormalUser normalUser;
}
