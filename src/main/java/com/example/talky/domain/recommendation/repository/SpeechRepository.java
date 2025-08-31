package com.example.talky.domain.recommendation.repository;

import com.example.talky.domain.recommendation.entity.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long> {
    @Query("""
select s
from Speech s
LEFT JOIN s.normalUser u
where u.id = :userId AND  s.createdAt >= :date
""")
    List<Speech> findAllByNormalUserId(
            @Param("userId") Long normalId,
            @Param("date") LocalDate date);
}
