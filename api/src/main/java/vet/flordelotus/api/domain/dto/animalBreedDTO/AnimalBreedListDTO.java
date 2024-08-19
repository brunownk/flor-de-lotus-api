package vet.flordelotus.api.domain.dto.animalBreedDTO;

import vet.flordelotus.api.domain.dto.animalDTO.AnimalListDTO;
import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;

public record AnimalBreedListDTO(
        Long id,
        String name,
        AnimalType animalType
) {

    public AnimalBreedListDTO(AnimalBreed breed) {
        this(breed.getId(), breed.getName(), breed.getAnimalType());
    }
}