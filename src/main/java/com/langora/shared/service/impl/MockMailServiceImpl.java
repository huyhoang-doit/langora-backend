package com.langora.shared.service.impl;

import org.springframework.stereotype.Service;

import com.langora.shared.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MockMailServiceImpl implements MailService {
    @Override
    public void sendEmail(String to, String subject, String content) {
        log.info("================ MOCK EMAIL ================");
        log.info("To: {}", to);
        log.info("Subject: {}", subject);
        log.info("Content: {}", content);
        log.info("============================================");
    }
}
