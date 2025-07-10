package com.example.talky.domain.auth.repository;

import com.example.talky.domain.auth.entity.Guardians;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardians, Long> {
    // 아이디 존재 확인
    boolean existsByLoginId(String loginId);
    // 보호자 정보 조회
    Optional<Guardians> findByLoginId(String loginId);
}
