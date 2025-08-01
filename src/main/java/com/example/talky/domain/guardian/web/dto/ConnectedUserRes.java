package com.example.talky.domain.guardian.web.dto;

import com.example.talky.domain.auth.entity.NormalUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConnectedUserRes {
    private Long id;
    private String connectionCode;


    public static ConnectedUserRes from(NormalUser normalUser) {
        return ConnectedUserRes.builder()
                .id(normalUser.getId())
                .connectionCode(normalUser.getConnectionCode())
                .build();
    }
}
