package com.langora.shared.service;

public interface MailService {
    void sendEmail(String to, String subject, String content);
}
