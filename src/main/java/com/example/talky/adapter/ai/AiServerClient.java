package com.example.talky.adapter.ai;

public interface AiServerClient {
    // dto를 global/ai 내에 선언해두는게 나을듯.
    // FIXME
    // 아직은 선언하지 않았으므로, Object로 선언해두겠다.
    Object callAiServer(Object req);
}
