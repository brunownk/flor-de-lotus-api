package vet.flordelotus.api.validators.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vet.flordelotus.api.infra.exception.ExceptionValidation;
import vet.flordelotus.api.domain.repository.AppointmentRepository;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.AppointmentSchedulingValidator;

@Component
public class VetWithAnotherAppointmentsAtTheSameTimeValidator implements AppointmentSchedulingValidator {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var vetHasAnotherAppointmentAtTheSameTime = repository.existsByVeterinarianIdAndDateAndCancelAppointmentReasonIsNull(data.idVeterinarian(), data.date());
        if (vetHasAnotherAppointmentAtTheSameTime) {
            throw new ExceptionValidation("The vet already has another appointment scheduled at the same time");
        }
    }

}