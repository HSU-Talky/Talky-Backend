package com.example.talky.domain.recommendation.repository;

import com.example.talky.domain.recommendation.entity.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long> {
}
