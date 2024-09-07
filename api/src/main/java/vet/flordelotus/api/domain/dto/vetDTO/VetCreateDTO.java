package vet.flordelotus.api.domain.dto.vetDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import vet.flordelotus.api.enums.vet.Specialty;


public record VetCreateDTO(
        @NotNull(message = "The user ID is required.")
        Long userId,
        @NotBlank
        String crmv,
        @NotNull
        Specialty specialty
        ) {
}
