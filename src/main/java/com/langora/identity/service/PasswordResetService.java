package com.langora.identity.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.identity.domain.entity.PasswordReset;
import com.langora.identity.domain.entity.User;
import com.langora.identity.repository.PasswordResetRepository;
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
public class PasswordResetService {

    PasswordResetRepository passwordResetRepository;
    UserRepository userRepository;
    MailService mailService;
    PasswordEncoder passwordEncoder;

    @lombok.experimental.NonFinal
    @Value("${app.frontend.url:http://localhost:3000}")
    String frontendUrl;

    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String token = UUID.randomUUID().toString();

        PasswordReset passwordReset = PasswordReset.builder()
                .userId(user.getId())
                .resetToken(token)
                .createdAt(OffsetDateTime.now())
                .expiredAt(OffsetDateTime.now().plusHours(1)) // 1 hour validity
                .build();

        passwordResetRepository.save(passwordReset);

        String resetLink = frontendUrl + "/reset-password?token=" + token;
        String content = "Please click the link to reset your password: " + resetLink;
        mailService.sendEmail(user.getEmail(), "Reset your password - Langora", content);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordReset passwordReset = passwordResetRepository
                .findByResetToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED)); // Invalid token

        if (passwordReset.getUsedAt() != null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED); // Already used
        }

        if (passwordReset.getExpiredAt().isBefore(OffsetDateTime.now())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED); // Expired token
        }

        User user = userRepository
                .findById(passwordReset.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(OffsetDateTime.now());
        userRepository.save(user);

        passwordReset.setUsedAt(OffsetDateTime.now());
        passwordResetRepository.save(passwordReset);
    }
}
