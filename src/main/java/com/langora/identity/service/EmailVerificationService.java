package com.langora.identity.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.identity.domain.entity.EmailVerification;
import com.langora.identity.domain.entity.User;
import com.langora.identity.repository.EmailVerificationRepository;
import com.langora.identity.repository.UserRepository;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.shared.service.MailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailVerificationService {

    EmailVerificationRepository emailVerificationRepository;
    UserRepository userRepository;
    MailService mailService;

    @lombok.experimental.NonFinal
    @Value("${app.frontend.url:http://localhost:3000}")
    String frontendUrl;

    @Transactional
    public void requestVerification(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new AppException(ErrorCode.USER_EXISTED); // Or some EMAIL_ALREADY_VERIFIED code
        }

        String token = UUID.randomUUID().toString();

        EmailVerification verification = EmailVerification.builder()
                .userId(userId)
                .verificationToken(token)
                .createdAt(OffsetDateTime.now())
                .expiredAt(OffsetDateTime.now().plusHours(24)) // 24 hours validity
                .build();

        emailVerificationRepository.save(verification);

        String verificationLink = frontendUrl + "/verify-email?token=" + token;
        String content = "Please click the link to verify your email: " + verificationLink;
        mailService.sendEmail(user.getEmail(), "Verify your email - Langora", content);
    }

    @Transactional
    public void verifyEmail(String token) {
        EmailVerification verification = emailVerificationRepository
                .findByVerificationToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED)); // Invalid token

        if (verification.getVerifiedAt() != null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED); // Already verified
        }

        if (verification.getExpiredAt().isBefore(OffsetDateTime.now())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED); // Expired token
        }

        User user = userRepository
                .findById(verification.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setEmailVerified(true);
        userRepository.save(user);

        verification.setVerifiedAt(OffsetDateTime.now());
        emailVerificationRepository.save(verification);
    }
}
