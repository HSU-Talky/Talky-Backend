package com.example.talky.domain.favorites.entity;

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
public class Favorite extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    private String sentence;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_id")
    private NormalUser normalUser;

    public void increaseCount() {
        this.count++;
    }
}
