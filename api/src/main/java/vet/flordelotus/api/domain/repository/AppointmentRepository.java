package vet.flordelotus.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.flordelotus.api.domain.entity.Appointment;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByAnimalIdAndDateBetween(Long idAnimal, LocalDateTime firstTime, LocalDateTime lastTime);

    boolean existsByVeterinarianIdAndDateAndCancelAppointmentReasonIsNull(Long idVeterinarian, LocalDateTime date);
}
