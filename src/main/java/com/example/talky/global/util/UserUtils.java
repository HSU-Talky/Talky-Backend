package com.example.talky.global.util;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.global.exception.BaseException;
import com.example.talky.global.response.code.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;


    public String getUserType(User user) {
        if (user instanceof NormalUser) {
            return "normal";
        } else if (user instanceof Guardians) {
            return "guardian";
        } else {
            throw new BaseException(ErrorResponseCode.BAD_REQUEST_ERROR);
        }
    }

    public String generateConnectionCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
