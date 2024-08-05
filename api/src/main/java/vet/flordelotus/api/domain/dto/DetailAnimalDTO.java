package vet.flordelotus.api.domain.dto;

import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.animal.Gender;
import vet.flordelotus.api.domain.animal.Species;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetailAnimalDTO(
        Long id,
        String name,
        Long ownerId,
        Species species,
        String breed,
        Gender gender,
        LocalDate dateOfBirth,
        LocalDateTime createdAt,
        LocalDateTime deletedAt,
        Long createdById,
        Long deletedById,
        Boolean active) {

    public DetailAnimalDTO(Animal animal) {
        this(animal.getId(),
                animal.getName(),
                animal.getOwnerId(),
                animal.getSpecies(),
                animal.getBreed(),
                animal.getGender(),
                animal.getDateOfBirth(),
                animal.getCreatedAt(),
                animal.getDeletedAt(),
                animal.getCreatedById(),
                animal.getDeletedById(),
                animal.getActive());
    }
}

