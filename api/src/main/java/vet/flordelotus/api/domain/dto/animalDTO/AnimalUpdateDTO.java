package vet.flordelotus.api.domain.dto.animalDTO;

import jakarta.validation.constraints.NotNull;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.domain.Gender;

import java.time.LocalDate;

public record AnimalUpdateDTO(
        @NotNull(message = "The animal ID is required.")
        Long id,
        String name,
        AnimalType type,
        AnimalBreed breed,
        Gender gender,
        LocalDate dateOfBirth
) {}

