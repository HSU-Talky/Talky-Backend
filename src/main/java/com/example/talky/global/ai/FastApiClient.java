package com.example.talky.global.ai;

import com.example.talky.global.ai.dto.AiRcmdRes;
import com.example.talky.global.ai.dto.ToAiReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
@RequiredArgsConstructor
public class FastApiClient {

    @Autowired
    private final RestTemplate restTemplate;
    @Value("${ai.server.url}")
    private String url;

    /**
     * @param request RcmdServiceImpl에서 가공한 FastAPI 호출용 Request DTO 객체입니다.
     * @return category - 보호자 통계에서 일반 사용자의 사용 위치, recommendedSentences - List<String>으로 AI 서버에서 추천된 문장들입니다.
     * 이것들을 DTO 객체로 반환받습니다.
     */
    public AiRcmdRes getAiRcmd(ToAiReq request) {
        HttpEntity<ToAiReq> toAiReqHttpEntity = setRcmdRequest(request);
        return restTemplate.postForEntity(url + "/recommendations", toAiReqHttpEntity, AiRcmdRes.class).getBody();
    }

    /**
     * private 함수로, 객체 내부에서만 사용됩니다.
     * @param request RestTemplate로 요청을 보내기 위해서, Header 정보를 추가합니다.
     * @return Http Entity 객체를 반환합니다.
     */
    private HttpEntity<ToAiReq> setRcmdRequest(ToAiReq request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(request, headers);
    }
}
