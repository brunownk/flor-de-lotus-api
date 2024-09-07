package vet.flordelotus.api.domain.repository;

import vet.flordelotus.api.domain.dto.appointment.AppointmentCancelDTO;

public interface AppointmentCancellationValidator {

    void validate(AppointmentCancelDTO data);

}