package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.Appointment;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Method to check if an appointment exists for an animal within a certain time range
    boolean existsByAnimalIdAndDateBetween(Long idAnimal, LocalDateTime firstTime, LocalDateTime lastTime);

    // Method to check if a veterinarian has an appointment at a given date without a cancellation reason
    boolean existsByVeterinarianIdAndDateAndCancelAppointmentReasonIsNull(Long idVeterinarian, LocalDateTime date);

    // Method to find all active appointments (not canceled) with pagination
    Page<Appointment> findByCancelAppointmentReasonIsNull(Pageable pageable);

    @Query("""
                SELECT a FROM Appointment a
                JOIN a.veterinarian v
                JOIN a.animal an
                JOIN an.user u
                WHERE 
                    (a.date >= COALESCE(:startDate, a.date)) AND
                    (a.date <= COALESCE(:endDate, a.date))
            """)
    Page<Appointment> searchByDateRange(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        Pageable pageable);
}
