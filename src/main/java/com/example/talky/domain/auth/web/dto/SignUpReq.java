package com.example.talky.domain.auth.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpReq {

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String username;

    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    @Size(min = 5, max = 20, message = "아이디는 4~20자이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "영문 숫자 포함 5자 이상 입력 해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "영문과 숫자를 조합해 8자리 이상 비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "사용자 타입은 필수입니다.")
    private String userType;

}
