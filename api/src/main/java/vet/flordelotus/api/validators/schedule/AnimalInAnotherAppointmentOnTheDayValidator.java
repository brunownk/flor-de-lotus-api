package vet.flordelotus.api.validators.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vet.flordelotus.api.ExceptionValidation;
import vet.flordelotus.api.domain.repository.AppointmentRepository;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.AppointmentSchedulingValidator;

@Component
public class AnimalInAnotherAppointmentOnTheDayValidator implements AppointmentSchedulingValidator {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var firstTime = data.date().withHour(7);
        var lastTime = data.date().withHour(18);
        var animalHasAnotherAppointmentOnTheDay = repository.existsByAnimalIdAndDateBetween(data.idAnimal(), firstTime, lastTime);
        if (animalHasAnotherAppointmentOnTheDay) {
            throw new ExceptionValidation("Paciente já possui uma consulta agendada nesse dia");
        }
    }

}