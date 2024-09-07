package vet.flordelotus.api.validators.schedule;

import org.springframework.stereotype.Component;
import vet.flordelotus.api.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.AppointmentSchedulingValidator;

import java.time.Duration;
import java.time.LocalDateTime;

//generico
@Component("TimeAdvanceSchedulingValidator")
public class AdvanceTimeValidator implements AppointmentSchedulingValidator {

    public void validate(AppointmentScheduleDTO data) {
        var appointmentDate = data.date();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new ExceptionValidation("Appointment must be scheduled at least 30 minutes in advance");
        }

    }
}
