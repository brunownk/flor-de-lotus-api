package vet.flordelotus.api.domain.dto.appointment;

import vet.flordelotus.api.domain.entity.Appointment;

import java.time.LocalDateTime;

public record AppointmentDetailDTO(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public AppointmentDetailDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getVeterinarian().getId(), appointment.getAnimal().getId(), appointment.getDate());
    }
}
