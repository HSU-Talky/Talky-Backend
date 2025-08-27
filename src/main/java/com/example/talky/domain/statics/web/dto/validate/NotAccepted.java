package com.example.talky.domain.statics.web.dto.validate;

import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotAccepted {
    String message() default "위치정보 접근 동의한 사용자에 한해 긴급호출 사용 시 좌표 데이터를 전달하여야 합니다.";

    Class<? extends Payload>[] payload() default {};

    String longitude();

    String latitude();
}
