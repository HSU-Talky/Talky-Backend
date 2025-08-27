package com.example.talky.domain.emergency_history.repository;

import com.example.talky.domain.emergency_history.entity.EmergencyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyHistoryRepository extends JpaRepository<EmergencyHistory, Long> {
}
