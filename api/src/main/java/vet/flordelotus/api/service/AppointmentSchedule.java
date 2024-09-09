package vet.flordelotus.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vet.flordelotus.api.infra.exception.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentCancelDTO;
import vet.flordelotus.api.domain.dto.appointment.AppointmentDetailDTO;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.*;
import vet.flordelotus.api.domain.entity.Appointment;
import vet.flordelotus.api.domain.entity.Veterinarian;

import java.util.List;

@Service
public class AppointmentSchedule {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private VeterinarianRepository veterinarianRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<AppointmentSchedulingValidator> appointmentSchedulingValidators;
    @Autowired
    private List<AppointmentCancellationValidator> appointmentCancellationValidators;

    public AppointmentDetailDTO schedule(AppointmentScheduleDTO data) {

        if (!animalRepository.existsById(data.idAnimal())) {
            throw new ExceptionValidation("Patient ID provided does not exist!");
        }
        if (data.idVeterinarian() != null && !veterinarianRepository.existsById(data.idVeterinarian())) {
            throw new ExceptionValidation("Vet ID provided does not exist!");
        }
        appointmentSchedulingValidators.forEach(v -> v.validate(data));
        var animal = animalRepository.getReferenceById(data.idAnimal());
        var vet = chooseVet(data);
        if (vet == null) {
            throw new ExceptionValidation("There is no vet available on that date!");
        }

        var appointment = new Appointment(null, vet, animal, data.date(), null);
        appointmentRepository.save(appointment);

        return new AppointmentDetailDTO(appointment);
    }

    private Veterinarian chooseVet(AppointmentScheduleDTO data) {
        if (data.idVeterinarian() != null){
            return veterinarianRepository.getReferenceById(data.idVeterinarian());
        }
        if (data.specialty() == null){
            throw new ExceptionValidation("Specialty is mandatory when a vet is not chosen");
        }
        return userRepository.chooseRandomVetFreeOnDate(data.specialty(), data.date());
    }

    public void cancel(AppointmentCancelDTO data) {
        if (!appointmentRepository.existsById(data.idAppointment())) {
            throw new ExceptionValidation("The appointment ID provided does not exist!");
        }

        appointmentCancellationValidators.forEach(v -> v.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }

}
