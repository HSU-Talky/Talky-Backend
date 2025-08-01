package com.example.talky.domain.guardian.web.dto;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;

import java.util.List;
import java.util.stream.Collectors;

public record GuardianProfileRes(
        String userType,
        String username,
        String loginId,
        Boolean locationEnabled,
        List<ConnectedUserInfo> connectedUsers
) {

    private record ConnectedUserInfo(Long userId, String connectionCode) {
        public static ConnectedUserInfo from(NormalUser user) {
            return new ConnectedUserInfo(user.getId(), user.getConnectionCode());
        }
    }

    public static GuardianProfileRes from(Guardians user) {
        // 1. 연결된 사용자 리스트 변환 (connectionCode 중심)
        List<ConnectedUserInfo> connectedUserInfoList = user.getConnectedUsers().stream()
                .map(ConnectedUserInfo::from)
                .collect(Collectors.toList());

        // 2. 새로운 GuardianProfileRes 레코드 생성 및 반환
        return new GuardianProfileRes(
                "guardian",
                user.getUsername(),        // 보호자 이름
                user.getLoginId(),         // 보호자 아이디
                user.getLocationEnabled(),
                connectedUserInfoList      // 연결된 사용자들의 connectionCode 목록
        );
    }
}
