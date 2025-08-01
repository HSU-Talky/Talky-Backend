package com.example.talky.domain.guardian.service;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.exception.PermissionDeniedException;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.domain.guardian.web.dto.ConnectUserReq;
import com.example.talky.domain.guardian.web.dto.ConnectedUserRes;
import com.example.talky.domain.guardian.web.dto.GuardianProfileRes;
import com.example.talky.domain.guardian.web.dto.LocationAlertUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final UserRepository userRepository;

    private Guardians findGuardian(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!(user instanceof Guardians)) {
            throw new PermissionDeniedException();
        }
        return (Guardians) user;
    }

    @Override
    public GuardianProfileRes getGuardianProfile(Long guardianId) {
        Guardians guardian = findGuardian(guardianId);
        return GuardianProfileRes.from(guardian);
    }

    @Override
    public List<ConnectedUserRes> getConnectedUsers(Long guardianId) {
        Guardians guardian = findGuardian(guardianId);
        return guardian.getConnectedUsers().stream()
                .map(ConnectedUserRes::from)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void connectToUser(Long guardianId, ConnectUserReq connectUserReq) {
        Guardians guardian = findGuardian(guardianId);
        
        // connectionCode로 NormalUser 찾기
        NormalUser normalUser = userRepository.findByConnectionCode(connectUserReq.getConnectionCode())
                .filter(user -> user instanceof NormalUser)
                .map(user -> (NormalUser) user)
                .orElseThrow(UserNotFoundException::new);
        
        // 연결 설정
        normalUser.setGuardians(guardian);
    }

    @Transactional
    @Override
    public void disconnectFromUser(Long guardianId, Long normalUserId) {
        Guardians guardian = findGuardian(guardianId);
        
        NormalUser normalUser = userRepository.findById(normalUserId)
                .filter(user -> user instanceof NormalUser)
                .map(user -> (NormalUser) user)
                .orElseThrow(UserNotFoundException::new);
        
        // 해당 Guardian과 연결된 사용자인지 확인
        if (!normalUser.getGuardians().equals(guardian)) {
            throw new PermissionDeniedException();
        }
        
        // 연결 해제
        normalUser.setGuardians(null);
    }

    @Transactional
    @Override
    public void updateLocationAlert(Long guardianId, LocationAlertUpdateReq locationAlertUpdateReq) {
        Guardians guardian = findGuardian(guardianId);
        guardian.setLocationEnabled(locationAlertUpdateReq.getLocationEnabled());
    }
}
