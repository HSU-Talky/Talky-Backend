package com.example.talky.domain.favorites.repository;

import com.example.talky.domain.favorites.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByNormalUserId(Long normalUserId);
    Optional<Favorite> findByNormalUserIdAndSentence(Long normalUser_id, String sentence);
    boolean existsByNormalUserIdAndSentence(Long normalUserId, String sentence);
    List<Favorite> findTop5ByNormalUserIdOrderByCountDesc(Long normalUserId);

    boolean findAllBySentence(String sentence);
}
