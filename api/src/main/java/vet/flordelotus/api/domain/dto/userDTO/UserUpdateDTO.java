package vet.flordelotus.api.domain.dto.userDTO;

import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
        @NotNull(message = "The type ID is required.")
        Long id,
        String login,
        String password
) {}

