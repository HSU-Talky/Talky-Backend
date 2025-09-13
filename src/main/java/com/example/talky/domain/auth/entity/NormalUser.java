package com.example.talky.domain.auth.entity;

import com.example.talky.domain.recommendation.entity.Speech;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("NORMAL")
@SuperBuilder
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

    @JsonBackReference
    @OneToMany(mappedBy = "normalUser")
    private List<Speech> speech;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private Guardians guardians;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_phone")
    private String guardianPhone;

    private boolean isAcceptedLocationInfo;

    @Override
    public String getRole() {
        return "ROLE_NORMAL";
    }


    @PrePersist
    public void setDefault(){
        this.emergencyTarget = "119";
        this.ttsSpeed = 1.0;
        this.ttsLanguage = "ko";
        this.ttsGender = "female";
        this.isAcceptedLocationInfo = false;
    }

    public void toggleIsAcceptedLocationInfo(){
        this.isAcceptedLocationInfo = !this.isAcceptedLocationInfo;
    }
}
