package vet.flordelotus.api.domain.dto.vetDTO;

import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.enums.vet.Specialty;

public record VetListDTO(Long id, String name, String crmv, Specialty specialty) {

    public VetListDTO(Veterinarian veterinarian) {
        this(veterinarian.getId(), veterinarian.getName(), veterinarian.getCrmv(), veterinarian.getSpecialty());
    }
}

