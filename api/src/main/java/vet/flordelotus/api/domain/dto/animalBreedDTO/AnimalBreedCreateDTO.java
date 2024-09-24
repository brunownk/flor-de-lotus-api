package vet.flordelotus.api.domain.dto.animalBreedDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeDTO;
import vet.flordelotus.api.domain.entity.AnimalType;

public record AnimalBreedCreateDTO(
        @NotBlank(message = "The breed name is required.")
        @Size(min = 1, max = 100, message = "The name must be between 1 and 100 characters.")
        String name,

        @NotNull(message = "The type ID is required.")
        Long animalTypeId,

        @NotNull(message = "The creator ID is required.")
        Long createdById
) {}
