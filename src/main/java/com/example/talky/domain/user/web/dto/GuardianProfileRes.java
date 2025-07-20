package com.example.talky.domain.user.web.dto;

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

    private record ConnectedUserInfo(Long userId, String username) {
        public static ConnectedUserInfo from(NormalUser user) {
            return new ConnectedUserInfo(user.getId(), user.getUsername());
        }
    }

    public static GuardianProfileRes from(Guardians user) {
        // 1. 연결된 사용자 리스트 변환
        List<ConnectedUserInfo> connectedUserInfoList = user.getConnectedUsers().stream()
                .map(ConnectedUserInfo::from)
                .collect(Collectors.toList());

        // 2. 새로운 GuardianProfileRes 레코드 생성 및 반환
        return new GuardianProfileRes(
                "guardian",
                user.getUsername(),
                user.getLoginId(),
                user.getLocationEnabled(),
                connectedUserInfoList // 1번에서 만든 리스트를 전달
        );
    }
}
