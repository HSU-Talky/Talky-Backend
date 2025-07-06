package com.example.talky.domain.auth.entity;

import com.example.talky.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "normal_id")
    private Long id;

    @Column(name="login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "connection_code")
    private String connectionCode;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "emergency_target")
    private String emergencyTarget;

    @Column(name = "tts_speed")
    private Long ttsSpeed;

    @Column(name = "tts_language")
    private String ttsLanguage;

    @Column(name = "tts_gender")
    private String ttsGender;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private Guardians guardians;

    @PrePersist
    public void setDefault(){
        this.ttsSpeed = 50L;
        this.ttsLanguage = "ko";
        this.ttsGender = "female";
    }


}
