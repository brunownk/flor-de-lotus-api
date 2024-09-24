package vet.flordelotus.api.domain.dto.animalDTO;

import jakarta.validation.constraints.NotNull;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.enums.animal.Gender;

import java.time.LocalDate;

public record AnimalUpdateDTO(
        @NotNull(message = "The animal ID is required.")
        Long id,
        String name,
        Long animalBreedId,
        Gender gender,
        LocalDate dateOfBirth
) {}

