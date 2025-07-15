package com.example.talky.adapter.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class AiServerClientImpl implements AiServerClient {

    private final RestTemplate restTemplate;

    @Override
    public Object callAiServer(Object req) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:5000")
                .path("/ai")
                .encode()
                .build()
                .toUri();

        // 추후에 Object를 받을 Res로 선언. 이것도 global 패키지에 선언하면 될듯.
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri, req, Object.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Ai 서버 호출 실패" + responseEntity.getStatusCode());
        }
    }
}
