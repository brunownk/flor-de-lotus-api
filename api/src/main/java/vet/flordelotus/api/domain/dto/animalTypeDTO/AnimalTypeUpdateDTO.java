package vet.flordelotus.api.domain.dto.animalTypeDTO;

import jakarta.validation.constraints.NotNull;

public record AnimalTypeUpdateDTO(
        @NotNull(message = "The type ID is required.")
        Long id,
        String name
) {}