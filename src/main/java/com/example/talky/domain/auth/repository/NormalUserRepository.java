package com.example.talky.domain.auth.repository;

import com.example.talky.domain.auth.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NormalUserRepository extends JpaRepository<NormalUser,Long> {
    boolean existsByLoginId(String loginId);
    Optional<NormalUser> findByLoginId(String loginId);


}
