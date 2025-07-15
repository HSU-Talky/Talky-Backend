package com.example.talky.adapter.ai;

import com.example.talky.global.ai.dto.FromAiRes;
import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class AiServerClientImpl implements AiServerClient {

    private final RestTemplate restTemplate;

    // FIXME
    @Override
    public ResponseEntity<SuccessResponse<?>> callAiServer(ToAiReq req) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:5000") // FastAPI 서버 띄워지는 주소로 변경
                .path("/ai") // 해당 path로 수정 요망
                .encode()
                .build()
                .toUri();

        // 추후에 Object를 받을 Res로 선언. 이것도 global 패키지에 선언하면 될듯 -> 완
        ResponseEntity<SuccessResponse<?>> responseEntity = restTemplate
                .exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(req),
                        new ParameterizedTypeReference<SuccessResponse<?>> () {}
                );

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity;
        } else {
            // 새로운 예외 선언
            // FIXME
            throw new RuntimeException("Ai 서버 호출 실패" + responseEntity.getStatusCode());
        }
    }
}
