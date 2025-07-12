package com.example.talky.domain.auth.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // loginId로 모든 타입의 사용자 조회
    Optional<User> findByLoginId(String loginId);

    // loginId 존재 여부 확인
    boolean existsByLoginId(String loginId);
}
