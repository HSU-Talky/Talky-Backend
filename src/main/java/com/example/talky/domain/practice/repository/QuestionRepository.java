package com.example.talky.domain.practice.repository;

import com.example.talky.domain.practice.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByPracId(Long pracId);
}
