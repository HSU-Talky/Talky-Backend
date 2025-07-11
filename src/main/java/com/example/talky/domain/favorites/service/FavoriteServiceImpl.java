package com.example.talky.domain.favorites.service;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.repository.NormalUserRepository;
import com.example.talky.domain.favorites.entity.Favorite;
import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteReq;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final NormalUserRepository normalUserRepository;

    @Override
    public CreateFavoriteRes create(Long nomalId, CreateFavoriteReq createFavoriteReq) {
        // 아직 normalUserRepository 예외에 NotFoundException이 없음
        NormalUser user = normalUserRepository
                .findById(nomalId)
                .orElseThrow(RuntimeException::new);

        Favorite favorite = Favorite.builder()
                .normalUser(user)
                .sentence(createFavoriteReq.getSentence())
                .count(0)
                .build();

        Favorite res = favoriteRepository.save(favorite);
        return new CreateFavoriteRes(res.getFavoriteId());
    }
}
