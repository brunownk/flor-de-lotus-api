package vet.flordelotus.api.domain.dto.vetDTO;

import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.enums.vet.Specialty;

public record VetDetailDTO(Long id, String name, String crmv, Specialty specialty) {

    public VetDetailDTO(Veterinarian veterinarian) {
        this(veterinarian.getId(), veterinarian.getName(), veterinarian.getCrmv(), veterinarian.getSpecialty());
    }
}
