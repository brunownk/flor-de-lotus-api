package vet.flordelotus.api.domain.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import vet.flordelotus.api.enums.vet.Specialty;

import java.time.LocalDateTime;


public record AppointmentScheduleDTO(
        Long idVeterinarian,

        @NotNull
        Long idAnimal,

        @NotNull
        @Future
        LocalDateTime date,

        Specialty specialty) {
}
