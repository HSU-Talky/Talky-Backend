package com.example.talky.domain.history.repository;

import com.example.talky.domain.history.entity.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Integer> {
}
