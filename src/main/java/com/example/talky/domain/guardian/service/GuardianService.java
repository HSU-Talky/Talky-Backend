package com.example.talky.domain.guardian.service;

import com.example.talky.domain.guardian.web.dto.ConnectUserReq;
import com.example.talky.domain.guardian.web.dto.ConnectedUserRes;
import com.example.talky.domain.guardian.web.dto.GuardianProfileRes;
import com.example.talky.domain.guardian.web.dto.GuardianUsernameUpdateReq;
import com.example.talky.domain.guardian.web.dto.LocationAlertUpdateReq;

import java.util.List;

public interface GuardianService {
    // 보호자 프로필 조회
    GuardianProfileRes getGuardianProfile(Long guardianId);
    
    // 연결된 사용자 목록 조회
    List<ConnectedUserRes> getConnectedUsers(Long guardianId);
    
    // 사용자 연결
    void connectToUser(Long guardianId, ConnectUserReq connectUserReq);

    // 사용자 연결 해제
    void disconnectFromUser(Long guardianId, Long normalUserId);

    // 위치 알림 설정 변경
    void updateLocationAlert(Long guardianId, LocationAlertUpdateReq locationAlertUpdateReq);
    
    void updateUsername(Long guardianId, GuardianUsernameUpdateReq guardianUsernameUpdateReq);
}
