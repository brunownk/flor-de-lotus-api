package vet.flordelotus.api.domain.dto.appointment;

import jakarta.validation.constraints.NotNull;
import vet.flordelotus.api.enums.appointment.CancelAppointmentReason;

public record AppointmentCancelDTO(
        @NotNull
        Long idAppointment,

        @NotNull
        CancelAppointmentReason reason) {
}