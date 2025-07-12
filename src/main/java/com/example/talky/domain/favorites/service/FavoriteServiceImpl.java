package com.example.talky.domain.favorites.service;

import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.favorites.entity.Favorite;
import com.example.talky.domain.favorites.exception.ConflictFavoriteException;
import com.example.talky.domain.favorites.exception.FavoriteNorFoundException;
import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.favorites.web.dto.AllFavoriteRes;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteReq;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteRes;
import com.example.talky.domain.favorites.web.dto.DeleteFavoriteReq;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final NormalUserRepository normalUserRepository;

    // 새 즐겨찾기 생성
    @Override
    public CreateFavoriteRes create(Long normalId, CreateFavoriteReq createFavoriteReq) {

        // FIXME
        // normal id를 그대로 전달해도 괜찮은지 check
        // 이미 존재하는 즐겨찾기인지 검증
        if (favoriteRepository.existsByNormalUserIdAndSentence(normalId, createFavoriteReq.getSentence())) {
            throw new ConflictFavoriteException();
        }

        /**
         * FIXME
         * NullPointerException -> UserNotFoundException
         */
        NormalUser user = normalUserRepository
                .findById(normalId)
                .orElseThrow(NullPointerException::new);

        Favorite favorite = Favorite.builder()
                .normalUser(user)
                .sentence(createFavoriteReq.getSentence())
                .count(0)
                .build();

        Favorite res = favoriteRepository.save(favorite);

        return new CreateFavoriteRes(res.getFavoriteId());
    }

    // 해당 유저의 모든 즐겨찾기 조회
    @Override
    public AllFavoriteRes getAllFavorite(Long normalId) {

        /**
         * FIXME
         * NullPointerException -> UserNotFoundException
         */
        NormalUser user = normalUserRepository
                .findById(normalId)
                .orElseThrow(NullPointerException::new);

        return new AllFavoriteRes(
                favoriteRepository.findAllByNormalUserId(normalId).stream()
                        .map(f -> new AllFavoriteRes.AllFavorite(
                                f.getSentence(),
                                f.getCount(),
                                f.getCreatedAt()
                        ))
                        .collect(Collectors.toList())
        );
    }

    // 즐겨찾기 삭제
    @Transactional
    @Override
    public void delete(Long normalId, DeleteFavoriteReq req) {
        String sentence = req.getSentence();

        /**
         * FIXME
         * NullPointerException -> UserNotFoundException
         */
        NormalUser user = normalUserRepository.findById(normalId)
                .orElseThrow(NullPointerException::new);
        Favorite favorite = favoriteRepository.findById(normalId)
                .orElseThrow(FavoriteNorFoundException::new);

        favoriteRepository.delete(favorite);
    }
}
