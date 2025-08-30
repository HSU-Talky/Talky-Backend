package com.example.talky.domain.emergency_history.entity;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyHistory extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "normal_id")
    private NormalUser user;

    private String target;

    private String roadAddress;

    private String longitude;
    private String latitude;

    @PrePersist
    private void prePersist() {
        this.target = user.getEmergencyTarget()
                .equals("119")? "119" : "보호자";
    }
}
