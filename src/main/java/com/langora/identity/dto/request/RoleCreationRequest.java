package com.langora.identity.dto.request;

import java.util.List;

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
public class RoleCreationRequest {

    @NotBlank(message = "Role code is required")
    String code;

    @NotBlank(message = "Role name is required")
    String name;

    String description;

    List<String> permissionIds;
}
