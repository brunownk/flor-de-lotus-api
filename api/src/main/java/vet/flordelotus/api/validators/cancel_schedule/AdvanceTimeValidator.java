package vet.flordelotus.api.validators.cancel_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vet.flordelotus.api.infra.exception.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentCancelDTO;
import vet.flordelotus.api.domain.repository.AppointmentCancellationValidator;
import vet.flordelotus.api.domain.repository.AppointmentRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("TimeAdvanceCancellationValidator")
public class AdvanceTimeValidator implements AppointmentCancellationValidator {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public void validate(AppointmentCancelDTO data) {
        var appointment = repository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, appointment.getDate()).toHours();

        if (differenceInHours < 24) {
            throw new ExceptionValidation("Appointments can only be cancelled with at least 24 hours' notice!");
        }
    }
}