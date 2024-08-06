package vet.flordelotus.api.domain.dto;

import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.animal.Gender;
import vet.flordelotus.api.domain.animal.Species;
import vet.flordelotus.api.domain.entity.User;

import java.time.LocalDate;

public record ListAnimalDTO(

        Long id,
        String name,

        User user,

        Species species,
        String breed,
        Gender gender,
        LocalDate dateOfBirth
) {

    public ListAnimalDTO(Animal animal) {
        this(animal.getId(), animal.getName(), animal.getUser(), animal.getSpecies(), animal.getBreed(), animal.getGender(), animal.getDateOfBirth());
    }

}
