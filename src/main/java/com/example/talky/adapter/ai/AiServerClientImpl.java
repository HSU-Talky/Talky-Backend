package com.example.talky.adapter.ai;

import com.example.talky.adapter.ai.dto.Sentence;
import com.example.talky.adapter.ai.response.RecommendationResponse;
import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.response.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class AiServerClientImpl implements AiServerClient {

    private final RestTemplate restTemplate;

    // FIXME
    @Override
    // Object -> SuccessResponse<?>
    public SuccessResponse<RecommendationResponse> callAiServer(ToAiReq req) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8000") // FastAPI 서버 띄워지는 주소로 변경
                .path("/recommendations") // 해당 path로 수정 요망
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ToAiReq> entity = new HttpEntity<>(req, headers);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonDebug = mapper.writeValueAsString(req);
            // log.info("AI 서버로 전송될 JSON: {}", jsonDebug);
        } catch (JsonProcessingException e) {
            log.error("JSON 직렬화 실패", e);
        }

        ResponseEntity<RecommendationResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        RecommendationResponse body = responseEntity.getBody();


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return SuccessResponse.created(body);
        } else {
            log.info("AI 서버 호출 실패" + responseEntity.getStatusCode());
            throw new RuntimeException("AI 서버 호출 실패" + responseEntity.getStatusCode());
        }
        
    }
}
