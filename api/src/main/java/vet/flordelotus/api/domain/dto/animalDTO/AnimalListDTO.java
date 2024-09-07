package vet.flordelotus.api.domain.dto.animalDTO;

import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.enums.animal.Gender;

import java.time.LocalDate;

public record AnimalListDTO(

        Long id,
        String name,
        AnimalType type,
        AnimalBreed breed,
        Gender gender,
        LocalDate dateOfBirth
) {

    public AnimalListDTO(Animal animal) {
        this(animal.getId(), animal.getName(), animal.getType(), animal.getBreed(), animal.getGender(), animal.getDateOfBirth());
    }
}

