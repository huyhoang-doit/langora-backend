package com.langora.identity.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.langora.identity.domain.entity.Role;
import com.langora.identity.domain.entity.User;
import com.langora.identity.domain.entity.UserRole;
import com.langora.identity.domain.enums.UserStatus;
import com.langora.identity.dto.request.AdminLoginRequest;
import com.langora.identity.dto.response.AdminAuthResponse;
import com.langora.identity.dto.response.AdminProfileResponse;
import com.langora.identity.repository.RoleRepository;
import com.langora.identity.repository.UserRepository;
import com.langora.identity.repository.UserRoleRepository;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    com.langora.user.repository.UserProfileRepository userProfileRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    public AdminAuthResponse login(AdminLoginRequest request) {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // Check if user is ADMIN
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        boolean isAdmin = false;
        for (UserRole ur : userRoles) {
            Role role = roleRepository.findById(ur.getRoleId()).orElse(null);
            if (role != null && "ADMIN".equals(role.getCode())) {
                isAdmin = true;
                break;
            }
        }

        if (!isAdmin) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String token = generateToken(user);

        return AdminAuthResponse.builder()
                .accessToken(token)
                .authenticated(true)
                .build();
    }

    public AdminProfileResponse getMe(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        List<String> roles = userRoles.stream()
                .map(ur -> roleRepository
                        .findById(ur.getRoleId())
                        .map(Role::getCode)
                        .orElse(""))
                .filter(code -> !code.isEmpty())
                .collect(Collectors.toList());

        com.langora.user.domain.entity.UserProfile profile =
                userProfileRepository.findByUserId(userId).orElse(null);

        return AdminProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(profile != null ? profile.getFullName() : null)
                .displayName(profile != null ? profile.getDisplayName() : null)
                .avatarUrl(profile != null ? profile.getAvatarUrl() : null)
                .dateOfBirth(profile != null ? profile.getDateOfBirth() : null)
                .gender(profile != null ? profile.getGender() : null)
                .countryCode(profile != null ? profile.getCountryCode() : null)
                .timezone(profile != null ? profile.getTimezone() : null)
                .bio(profile != null ? profile.getBio() : null)
                .roles(roles)
                .permissions(List.of()) // Pending Permissions implementation
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("langora-admin")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("email", user.getEmail())
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);

        try {
            JWSSigner signer = new MACSigner(SIGNER_KEY.getBytes());
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            log.error("Cannot create token", e);
            throw new RuntimeException("Cannot create token", e);
        }
    }
}
