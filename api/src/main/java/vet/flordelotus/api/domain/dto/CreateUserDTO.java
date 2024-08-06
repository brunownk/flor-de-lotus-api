package vet.flordelotus.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank(message = "O nome do proprietário é obrigatório.")
        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres.")
        String login,
        @NotBlank(message = "O nome do proprietário é obrigatório.")
        String password
) {}

