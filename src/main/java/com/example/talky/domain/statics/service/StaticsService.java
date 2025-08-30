package com.example.talky.domain.statics.service;

import com.example.talky.domain.statics.web.dto.StaticsRes;

public interface StaticsService {
    StaticsRes getNormalUsersStatics(Long normalId);
}
