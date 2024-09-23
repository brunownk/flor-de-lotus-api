package vet.flordelotus.api.domain.dto.appointment;

import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.Appointment;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.entity.Veterinarian;

import java.time.LocalDateTime;

public record AppointmentDetailDTO(
        Long id,
        Long vetId,
        String vetName,
        Long animalId,
        String animalName,
        LocalDateTime data) {
    public AppointmentDetailDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getVeterinarian().getId(), appointment.getVeterinarian().getUser().getName(), appointment.getAnimal().getId(), appointment.getAnimal().getName(), appointment.getDate());
    }
}
