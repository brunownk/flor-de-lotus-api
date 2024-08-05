package vet.flordelotus.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull String username,
        @NotNull String password,
        @NotNull String role,
        byte[] image
) {}
