package vet.flordelotus.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUserDTO(
        @NotNull Long id,
        String username,
        String password,
        String role,
        byte[] image
) {}
