package com.example.talky.domain.statics.web.dto.validate;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.exception.PermissionDeniedException;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.global.security.CustomUserDetails;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class CoordinateValidator implements ConstraintValidator<NotAccepted, String> {

    private final UserRepository userRepository;
    private String longitude;
    private String latitude;

    @Override
    public void initialize(NotAccepted constraintAnnotation) {
        this.longitude = constraintAnnotation.longitude();
        this.latitude = constraintAnnotation.latitude();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(UserNotFoundException::new);

        if(!(user instanceof NormalUser)){
            throw new PermissionDeniedException();
        }

        NormalUser normalUser = (NormalUser) user;

        if(normalUser.isAcceptedLocationInfo()) {
            // 위치정보 접근에 동의했을 때 필드 값이 true이므로, Getter로 접근 후 검증
            if(longitude.isBlank() || latitude.isBlank()) {
                    return false;
            }
        }

        // 조건문에서 반환되지 않음 -> 조건문에 진입하지 않음, 항상 검증하지 않음
        return true;
    }
}
