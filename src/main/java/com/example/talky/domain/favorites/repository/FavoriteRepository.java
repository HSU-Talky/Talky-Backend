package com.example.talky.domain.favorites.repository;

import com.example.talky.domain.favorites.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
