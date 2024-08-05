package vet.flordelotus.api.domain.dto;

import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.animal.Gender;
import vet.flordelotus.api.domain.animal.Species;

import java.time.LocalDate;

public record ListAnimalDTO(

        Long id,
        String name,
        Species species,
        String breed,
        Gender gender,
        LocalDate dateOfBirth
) {

    public ListAnimalDTO(Animal animal) {
        this(animal.getId(), animal.getName(), animal.getSpecies(), animal.getBreed(), animal.getGender(), animal.getDateOfBirth());
    }

}
