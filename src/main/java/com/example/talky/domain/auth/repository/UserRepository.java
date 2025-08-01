package com.example.talky.domain.auth.repository;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);
    Optional<User> findByLoginId(String loginId);
    @Query("SELECT u FROM NormalUser u WHERE u.connectionCode = :connectionCode")
    Optional<User> findByConnectionCode(String connectionCode);
}
