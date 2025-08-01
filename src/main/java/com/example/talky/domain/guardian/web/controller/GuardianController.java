package com.example.talky.domain.guardian.web.controller;

import com.example.talky.domain.guardian.service.GuardianService;
import com.example.talky.domain.guardian.web.dto.ConnectUserReq;
import com.example.talky.domain.guardian.web.dto.LocationAlertUpdateReq;
import com.example.talky.global.response.SuccessResponse;
import com.example.talky.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService guardianService;

    @PutMapping("/location-alert")
    public ResponseEntity<SuccessResponse<?>> updateLocationAlert(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                  @RequestBody @Valid LocationAlertUpdateReq locationAlertUpdateReq) {
        guardianService.updateLocationAlert(userDetails.getUser().getId(), locationAlertUpdateReq);
        return ResponseEntity.ok(SuccessResponse.empty());
    }

    @PostMapping("/connect")
    public ResponseEntity<SuccessResponse<?>> connectToUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody @Valid ConnectUserReq connectUserReq) {
        guardianService.connectToUser(userDetails.getUser().getId(), connectUserReq);
        return ResponseEntity.ok(SuccessResponse.empty());
    }

    @DeleteMapping("/connect/{normalUserId}")
    public ResponseEntity<SuccessResponse<?>> disconnectFromUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @PathVariable Long normalUserId) {
        guardianService.disconnectFromUser(userDetails.getUser().getId(), normalUserId);
        return ResponseEntity.ok(SuccessResponse.empty());
    }
}
