package vet.flordelotus.api.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vet.flordelotus.api.domain.animal.Species;

public record UpdateUserDTO(
        @NotNull
        Long id,
        String login,
        String password
) {}

