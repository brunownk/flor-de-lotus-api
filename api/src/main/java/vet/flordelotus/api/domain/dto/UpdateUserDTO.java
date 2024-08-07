package vet.flordelotus.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUserDTO(
        @NotNull
        Long id,
        String login,
        String password,
        String name,
        String username
) {}

