package com.example.talky.domain.auth.entity;

import com.example.talky.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("NORMAL")
public class NormalUser extends User{

    @Column(name = "connection_code")
    private String connectionCode;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "emergency_target")
    private String emergencyTarget;

    @Column(name = "tts_speed")
    private Double ttsSpeed;

    @Column(name = "tts_language")
    private String ttsLanguage;

    @Column(name = "tts_gender")
    private String ttsGender;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private Guardians guardians;

    @Override
    public String getRole() {
        return "ROLE_NORMAL";
    }


    @PrePersist
    public void setDefault(){
        this.emergencyTarget = "119";
        this.ttsSpeed = 50.0;
        this.ttsLanguage = "ko";
        this.ttsGender = "female";
    }


}
