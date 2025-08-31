package com.example.talky.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "guardians")
    private List<NormalUser> connectedUsers = new ArrayList<>();

    @Override
    public String getRole() {
        return "ROLE_GUARDIAN";
    }




}
