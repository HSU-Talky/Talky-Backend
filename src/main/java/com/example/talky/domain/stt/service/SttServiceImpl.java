package com.example.talky.domain.stt.service;

import com.example.talky.adapter.ai.AiServerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SttServiceImpl implements SttService {

    private final AiServerClient aiServerClient;

    @Override
    public String transcribe(MultipartFile audioFile) {
        if (audioFile == null || audioFile.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return aiServerClient.transcribe(audioFile);
    }
}
