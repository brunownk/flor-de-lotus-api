package vet.flordelotus.api.domain.dto.animalDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vet.flordelotus.api.enums.animal.Gender;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeDTO;

import java.time.LocalDate;

public record AnimalCreateDTO(

        @NotBlank(message = "The animal's name is required.")
        @Size(min = 1, max = 100, message = "The name must be between 1 and 100 characters.")
        String name,

        @NotNull(message = "The owner ID is required.")
        Long userId,

        @NotNull(message = "The gender is required.")
        Gender gender,

        LocalDate dateOfBirth,

        @NotNull(message = "The creator ID is required.")
        Long createdById,
        @NotNull(message = "The breed is required.")

        AnimalBreedDTO animalBreedId,
        @NotNull(message = "The type is required.")

        AnimalTypeDTO animalTypeId

) {}

