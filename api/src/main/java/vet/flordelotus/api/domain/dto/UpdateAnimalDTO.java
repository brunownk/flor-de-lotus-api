package vet.flordelotus.api.domain.dto;

import jakarta.validation.constraints.NotNull;
import vet.flordelotus.api.domain.animal.Gender;
import vet.flordelotus.api.domain.animal.Species;

import java.time.LocalDate;

public record UpdateAnimalDTO(
        @NotNull
        Long id,
        String name,
        Species species,
        String breed,
        Gender gender,
        LocalDate dateOfBirth
) {}
