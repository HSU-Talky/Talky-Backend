package com.example.talky.domain.guardian.web.dto;

import com.example.talky.domain.auth.entity.Guardians;

import java.util.List;
import java.util.stream.Collectors;

public record GuardianProfileRes(
        String userType,
        String username,
        String loginId,
        Boolean locationEnabled,
        List<ConnectedUserRes> connectedUsers
) {

    public static GuardianProfileRes from(Guardians user) {
        // 1. 연결된 사용자 리스트 변환
        List<ConnectedUserRes> connectedUserResList = user.getConnectedUsers().stream()
                .map(ConnectedUserRes::from)
                .collect(Collectors.toList());

        // 2. 새로운 GuardianProfileRes 레코드 생성 및 반환
        return new GuardianProfileRes(
                "guardian",
                user.getUsername(),        // 보호자 이름
                user.getLoginId(),         // 보호자 아이디
                user.getLocationEnabled(),
                connectedUserResList       // 연결된 사용자들의 정보
        );
    }
}
