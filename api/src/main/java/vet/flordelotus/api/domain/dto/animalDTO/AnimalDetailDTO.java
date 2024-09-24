package vet.flordelotus.api.domain.dto.animalDTO;

import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.enums.animal.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AnimalDetailDTO(
        Long id,
        String name,
        Long animalBreedId,
        String animalBreedName,
        Long animalTypeId,
        String animalTypeName,
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
                animal.getId(),
                animal.getBreed().getName(),
                animal.getType().getId(),
                animal.getType().getName(),
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
