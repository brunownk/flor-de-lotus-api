package vet.flordelotus.api.domain.dto.userDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank(message = "The owner's name is required.")
        @Size(min = 1, max = 100, message = "The name must be between 1 and 100 characters.")
        String login,

        @NotBlank(message = "The owner's name is required.")
        String password,
        String name,
        String username
) {}

