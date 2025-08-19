package com.example.talky.domain.history.entity;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.global.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Speech extends BaseEntity {
    /**
     * BaseEntity를 상속받음으로써
     * created_at으로 발화가 언제 이루어졌는지 추적 가능
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 피보호자와의 연관관계를 명시하는 필드
    // 피보호자 1명당 n개의 history가 있을 수 있으므로
    // ManyToOne 매핑
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "normal_id")
    private NormalUser normalUser;

    // 어디서 사용했는지 확인할 필드
    private String place;
}
