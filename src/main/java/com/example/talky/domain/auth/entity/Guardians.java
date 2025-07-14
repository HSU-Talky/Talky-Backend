package com.example.talky.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Guardians extends User {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "location_enabled")
    private Boolean locationEnabled;

    @Override
    public String getRole() {
        return "ROLE_GUARDIAN";
    }




}
