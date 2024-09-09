package vet.flordelotus.api.validators.schedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vet.flordelotus.api.infra.exception.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.AppointmentSchedulingValidator;
import vet.flordelotus.api.domain.repository.UserRepository;
import vet.flordelotus.api.domain.repository.VeterinarianRepository;

@Component
public class ActiveVeterinarianValidator implements AppointmentSchedulingValidator {

    @Autowired
    private VeterinarianRepository repository;
    @Autowired
    private UserRepository userRepository;

    public void validate(AppointmentScheduleDTO data) {
        if (data.idVeterinarian() == null) {
            return;
        }

        var vetIsActive = userRepository.findActiveById(data.idVeterinarian());
        if (!vetIsActive) {
            throw new ExceptionValidation("Appointment cannot be scheduled with excluded vet!");
        }
    }

}