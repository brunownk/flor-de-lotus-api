package vet.flordelotus.api.domain.dto.animalTypeDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnimalTypeCreateDTO(
        @NotBlank(message = "The type name is required.")
        @Size(min = 1, max = 100, message = "The name must be between 1 and 100 characters.")
        String name,

        @NotNull(message = "The creator ID is required.")
        Long createdById
) {}
