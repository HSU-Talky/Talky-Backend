package com.example.talky.history;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class LinkedNormalUserFindTest {

    @Test
    void findAllNormalUsers() {
        NormalUser normalUser = NormalUser.builder()
                .id(1L)
                .loginId("test1")
                .password("test1")
                .username("test1")
                .connectionCode(UUID.randomUUID().toString())
                .introduction("테스트")
                .emergencyTarget("GUARDIAN")
                .ttsSpeed(1.0)
                .ttsLanguage("kor")
                .ttsGender("male")
                .build();

        Guardians guardian = Guardians.builder()
                .id(2L)
                .loginId("test2")
                .password("test2")
                .username("test2")
                .phoneNumber("010-1111-1111")
                .locationEnabled(true)
                // ConnectUserReq.getConnectionCode
                // connectToUser
                .build();

        normalUser.setGuardians(guardian);
        guardian.setConnectedUsers(List.of(normalUser));

        List<NormalUser> connectedUsers = guardian.getConnectedUsers();

        System.out.println(connectedUsers);
        System.out.println(connectedUsers.get(0).equals(normalUser));
    }
}
