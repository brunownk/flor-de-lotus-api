package vet.flordelotus.api.domain.dto.animalTypeDTO;

import vet.flordelotus.api.domain.entity.AnimalType;

import java.time.LocalDateTime;

public record AnimalTypeDetailDTO(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime deletedAt,
        Long createdById,
        Long deletedById) {

    public AnimalTypeDetailDTO(AnimalType type) {
        this(type.getId(),
                type.getName(),
                type.getCreatedAt(),
                type.getDeletedAt(),
                type.getCreatedById(),
                type.getDeletedById());
    }
}