package com.langora.ai.controller.testAI;

import org.springframework.stereotype.Service;

@Service
public class ChatService {
    public String chat(ChatRequest request) {
        return request.message();
    }
}
