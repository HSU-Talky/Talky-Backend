package com.example.talky.domain.emergency_history.repository;

import com.example.talky.domain.emergency_history.entity.EmergencyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EmergencyHistoryRepository extends JpaRepository<EmergencyHistory, Long> {
    @Query("""
select
    eh
from EmergencyHistory eh
join NormalUser n
where n.id = :normalId
    and eh.createdAt >= :firstDay
""")
    List<EmergencyHistory> findAllByNormalId(
            @Param("firstDay")LocalDateTime firstDay
            );
}
