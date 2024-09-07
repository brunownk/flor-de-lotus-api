package vet.flordelotus.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vet.flordelotus.api.ExceptionValidation;
import vet.flordelotus.api.domain.dto.appointment.AppointmentCancelDTO;
import vet.flordelotus.api.domain.dto.appointment.AppointmentDetailDTO;
import vet.flordelotus.api.domain.dto.appointment.AppointmentScheduleDTO;
import vet.flordelotus.api.domain.repository.AppointmentCancellationValidator;
import vet.flordelotus.api.domain.repository.AppointmentSchedulingValidator;
import vet.flordelotus.api.domain.entity.Appointment;
import vet.flordelotus.api.domain.repository.AnimalRepository;
import vet.flordelotus.api.domain.repository.AppointmentRepository;
import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.domain.repository.VeterinarianRepository;

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
    private List<AppointmentSchedulingValidator> appointmentSchedulingValidators;
    @Autowired
    private List<AppointmentCancellationValidator> appointmentCancellationValidators;

    public AppointmentDetailDTO schedule(AppointmentScheduleDTO data) {

        if (!animalRepository.existsById(data.idAnimal())) {
            throw new ExceptionValidation("Id do paciente informado nao existe!");
        }
        if (data.idVeterinarian() != null && !veterinarianRepository.existsById(data.idVeterinarian())) {
            throw new ExceptionValidation("Id do medico informado nao existe!");
        }
        appointmentSchedulingValidators.forEach(v -> v.validate(data));
        var animal = animalRepository.getReferenceById(data.idAnimal());
        var vet = chooseVet(data);
        if (vet == null) {
            throw new ExceptionValidation("Não existe médico disponível nessa data!");
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
            throw new ExceptionValidation("Especialidade e obrigatoria quando medico nao for escolhido");
        }
        return veterinarianRepository.escolherMedicoAleatorioLivreNaData(data.specialty(), data.date());
    }

    public void cancel(AppointmentCancelDTO data) {
        if (!appointmentRepository.existsById(data.idAppointment())) {
            throw new ExceptionValidation("Id da consulta informado não existe!");
        }

        appointmentCancellationValidators.forEach(v -> v.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }

}
