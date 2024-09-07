package vet.flordelotus.api.validators.schedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vet.flordelotus.api.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.AppointmentSchedulingValidator;
import vet.flordelotus.api.domain.repository.VeterinarianRepository;

@Component
public class ActiveVeterinarianValidator implements AppointmentSchedulingValidator {

    @Autowired
    private VeterinarianRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        if (data.idVeterinarian() == null) {
            return;
        }

        var vetIsActive = repository.findActiveById(data.idVeterinarian());
        if (!vetIsActive) {
            throw new ExceptionValidation("Consulta não pode ser agendada com médico excluído");
        }
    }

}