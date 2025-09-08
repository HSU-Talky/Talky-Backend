package com.example.talky.domain.stt.service;

import org.springframework.web.multipart.MultipartFile;

public interface SttService {

    String transcribe(MultipartFile audioFile);
}
