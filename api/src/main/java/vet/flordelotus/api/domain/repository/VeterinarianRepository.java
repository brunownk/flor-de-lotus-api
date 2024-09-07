package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vet.flordelotus.api.enums.vet.Specialty;
import vet.flordelotus.api.domain.entity.Veterinarian;

import java.time.LocalDateTime;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    Page<Veterinarian> findAllByActiveTrue(Pageable paginacao);

    @Query("""
                select v from Veterinarian v
                           where
                           v.active = true
                           and
                           v.specialty = :specialty
                           and
                           v.id not in(
                               select a.veterinarian.id from Appointment a
                               where
                               a.date = :date
                               and
                               a.cancelAppointmentReason is null
                           )
                           order by rand()
                           limit 1
                           
            """)
    Veterinarian escolherMedicoAleatorioLivreNaData(Specialty specialty, LocalDateTime date);

    @Query("""
            select v.active
            from Veterinarian v
            where
            v.id = :id
            """)
    Boolean findActiveById(Long id);
}

