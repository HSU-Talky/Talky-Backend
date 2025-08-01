package com.example.talky.domain.user.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TtsUpdateReq {
    private Double ttsSpeed;
    private String ttsLanguage;
    private String ttsGender;
}
