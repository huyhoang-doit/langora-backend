package com.langora.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientRegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    String email;

    @NotBlank(message = "Password is required")
    String password;

    @NotBlank(message = "Full name is required")
    String fullName;

    @NotBlank(message = "Target language is required")
    String targetLanguageId;

    @NotBlank(message = "Current level is required")
    String currentLevelId;
}
