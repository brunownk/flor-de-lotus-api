package vet.flordelotus.api.validators.schedule;

import org.springframework.stereotype.Component;
import vet.flordelotus.api.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.AppointmentSchedulingValidator;

import java.time.DayOfWeek;
@Component
public class ClinicOpeningHoursValidator implements AppointmentSchedulingValidator {

    public void validate(AppointmentScheduleDTO date) {
        var appointmentDate = date.date();

        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeClinicOpening = appointmentDate.getHour() < 9;
        var afterClinicClosing = appointmentDate.getHour() > 19;
        if (sunday || beforeClinicOpening || afterClinicClosing) {
            throw new ExceptionValidation("Consulta fora do horário de funcionamento da clínica");
        }

    }

}

