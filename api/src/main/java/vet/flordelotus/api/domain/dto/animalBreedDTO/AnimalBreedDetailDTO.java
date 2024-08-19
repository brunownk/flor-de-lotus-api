package vet.flordelotus.api.domain.dto.animalBreedDTO;

import vet.flordelotus.api.domain.dto.animalDTO.AnimalListDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeDTO;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;

import java.time.LocalDateTime;

public record AnimalBreedDetailDTO(
        Long id,
        String name,
        AnimalType animalType,

        LocalDateTime createdAt,
        LocalDateTime deletedAt,
        Long createdById,
        Long deletedById) {

    public AnimalBreedDetailDTO(AnimalBreed breed) {
        this(breed.getId(),
                breed.getName(),
                breed.getAnimalType(),
                breed.getCreatedAt(),
                breed.getDeletedAt(),
                breed.getCreatedById(),
                breed.getDeletedById());
    }
}