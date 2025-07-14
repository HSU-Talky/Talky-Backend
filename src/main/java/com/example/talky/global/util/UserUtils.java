package com.example.talky.global.util;

import com.example.talky.domain.auth.entity.Guardians;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import com.example.talky.domain.auth.repository.GuardianRepository;
import com.example.talky.domain.auth.repository.NormalUserRepository;
import com.example.talky.global.exception.BaseException;
import com.example.talky.global.response.code.ErrorResponseCode;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
public class UserUtils {

    private final NormalUserRepository normalUserRepository;
    private final GuardianRepository guardianRepository;

    public UserUtils(NormalUserRepository normalUserRepository, GuardianRepository guardianRepository) {
        this.normalUserRepository = normalUserRepository;
        this.guardianRepository = guardianRepository;
    }

    public Optional<User> findUserByLoginId(String loginId) {
        Optional<NormalUser> normalUser = normalUserRepository.findByLoginId(loginId);
        if (normalUser.isPresent()) {
            return Optional.of(normalUser.get());
        }

        Optional<Guardians> guardian = guardianRepository.findByLoginId(loginId);
        return guardian.map(g -> g);
    }

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
