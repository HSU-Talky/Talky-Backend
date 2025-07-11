package com.example.talky.domain.favorites.service;

import com.example.talky.domain.favorites.web.dto.AllFavoriteRes;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteReq;
import com.example.talky.domain.favorites.web.dto.CreateFavoriteRes;

public interface FavoriteService {
    CreateFavoriteRes create(Long normalId, CreateFavoriteReq createFavoriteReq);
    AllFavoriteRes getAllFavorite(Long normalId);
}
