package com.langora.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.langora.ai.domain.entity.AiPrompt;
import com.langora.ai.dto.response.AiFeedbackResultDto;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AiGenerationService {

    private final ChatClient chatClient;

    public AiGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public AiFeedbackResultDto generateFeedback(AiPrompt prompt, String originalSentence, String userAnswer) {

        String systemContent = prompt.getSystemPrompt();
        String userContent = "Original sentence: " + (originalSentence != null ? originalSentence : "N/A")
                + "\nUser answer: " + userAnswer;

        try {
            return chatClient
                    .prompt()
                    .system(systemContent)
                    .user(userContent)
                    .call()
                    .entity(AiFeedbackResultDto.class);
        } catch (Exception e) {
            log.error("Error generating AI feedback using ChatClient", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXEPTION, "Error generating AI feedback: " + e.getMessage());
        }
    }
}
