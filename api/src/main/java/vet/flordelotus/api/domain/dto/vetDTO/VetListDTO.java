package vet.flordelotus.api.domain.dto.vetDTO;

import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.enums.vet.Specialty;

public record VetListDTO(
        Long id,
        String crmv,
        Specialty specialty,
        String username,
        String name) {

    public VetListDTO(Veterinarian veterinarian) {
        this(
                veterinarian.getId(),
                veterinarian.getCrmv(),
                veterinarian.getSpecialty(),
                veterinarian.getUser().getUsername(),
                veterinarian.getUser().getName());
    }

}

