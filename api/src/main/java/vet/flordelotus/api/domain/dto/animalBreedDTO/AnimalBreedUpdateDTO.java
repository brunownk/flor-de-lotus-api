package vet.flordelotus.api.domain.dto.animalBreedDTO;

import jakarta.validation.constraints.NotNull;

public record AnimalBreedUpdateDTO(
        @NotNull(message = "The breed ID is required.")
        Long id,
        String name
) {}