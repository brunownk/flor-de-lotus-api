package vet.flordelotus.api.domain.repository;

import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;

public interface AppointmentSchedulingValidator {

    void validate(AppointmentScheduleDTO data);

}
