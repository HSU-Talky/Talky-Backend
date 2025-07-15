package com.example.talky.domain.recommendation.repository;

import com.example.talky.domain.recommendation.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    void deleteAllByNormalUserId(Long normalId);
}
