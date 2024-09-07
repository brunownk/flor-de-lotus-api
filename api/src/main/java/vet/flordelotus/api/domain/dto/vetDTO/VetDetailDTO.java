package vet.flordelotus.api.domain.dto.vetDTO;

import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.enums.vet.Specialty;

public record VetDetailDTO(
        Long id,
        String crmv,
        Specialty specialty,
        String login,
        String username,
        String name)  {

    public VetDetailDTO(Veterinarian veterinarian) {
        this(
                veterinarian.getId(),
                veterinarian.getCrmv(),
                veterinarian.getSpecialty(),
                veterinarian.getUser().getLogin(),
                veterinarian.getUser().getUsername(),
                veterinarian.getUser().getName());
    }
}
