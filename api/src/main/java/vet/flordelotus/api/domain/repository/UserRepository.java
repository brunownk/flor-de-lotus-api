package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.enums.vet.Specialty;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
    UserDetails findByEmail(String email);

    Page<User> findAllByActiveTrue(Pageable paginacao);

    @Query("""
    select v from Veterinarian v
    where
    v.user.active = true
    and
    v.specialty = :specialty
    and
    v.id not in (
        select a.veterinarian.id from Appointment a
        where a.date = :date
        and a.cancelAppointmentReason is null
    )
    order by random()
    limit 1
""")
    Veterinarian chooseRandomVetFreeOnDate(Specialty specialty, LocalDateTime date);

    @Query("select v.user.active from Veterinarian v where v.id = :id")

    Boolean findActiveById(Long id);

}
