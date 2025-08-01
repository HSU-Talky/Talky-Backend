package com.example.talky.domain.guardian.service;

import com.example.talky.domain.guardian.web.dto.ConnectUserReq;
import com.example.talky.domain.guardian.web.dto.LocationAlertUpdateReq;

public interface GuardianService {
    // 사용자 연결
    void connectToUser(Long guardianId, ConnectUserReq connectUserReq);

    // 사용자 연결 해제
    void disconnectFromUser(Long guardianId, Long normalUserId);

    // 위치 알림 설정 변경
    void updateLocationAlert(Long guardianId, LocationAlertUpdateReq locationAlertUpdateReq);
}
