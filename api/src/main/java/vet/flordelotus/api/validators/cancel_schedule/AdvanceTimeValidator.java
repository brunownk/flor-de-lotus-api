package vet.flordelotus.api.validators.cancel_schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vet.flordelotus.api.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentCancelDTO;
import vet.flordelotus.api.domain.repository.AppointmentCancellationValidator;
import vet.flordelotus.api.domain.repository.AppointmentRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class AdvanceTimeValidator implements AppointmentCancellationValidator {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public void validate(AppointmentCancelDTO data) {
        var appointment = repository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, appointment.getDate()).toHours();

        if (differenceInHours < 24) {
            throw new ExceptionValidation("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}