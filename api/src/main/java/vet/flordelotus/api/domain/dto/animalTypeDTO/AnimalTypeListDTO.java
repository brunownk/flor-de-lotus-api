package vet.flordelotus.api.domain.dto.animalTypeDTO;

import vet.flordelotus.api.domain.entity.AnimalType;

public record AnimalTypeListDTO(
        Long id,
        String name
) {

    public AnimalTypeListDTO(AnimalType type) {
        this(type.getId(), type.getName());
    }
}