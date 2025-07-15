package com.example.talky.domain.recommendation.service;

import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.domain.recommendation.web.dto.RcmdSentenceRes;

public interface RcmdService {
    RcmdSentenceRes getAiRcmd(GetContextReq req);
}
