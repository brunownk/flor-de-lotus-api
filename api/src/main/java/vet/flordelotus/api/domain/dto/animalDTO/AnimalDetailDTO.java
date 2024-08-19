package vet.flordelotus.api.domain.dto.animalDTO;

import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.domain.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AnimalDetailDTO(
        Long id,
        String name,
        AnimalType type,
        AnimalBreed breed,
        Gender gender,
        LocalDate dateOfBirth,
        LocalDateTime createdAt,
        LocalDateTime deletedAt,
        Long createdById,
        Long deletedById,
        Boolean active) {

    public AnimalDetailDTO(Animal animal) {
        this(animal.getId(),
                animal.getName(),
                animal.getType(),
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


//Adicionar de volta depois que criarmos as roles
// animal.getOwnerId(),
