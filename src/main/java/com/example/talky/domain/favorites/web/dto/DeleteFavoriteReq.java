package com.example.talky.domain.favorites.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteFavoriteReq {
    @NotBlank(message = "즐겨찾기 문장은 비어있을 수 없습니다.")
    String sentence;
}
