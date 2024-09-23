package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.enums.vet.Specialty;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    UserDetails findByUsername(String username);

    // Find user by email
    UserDetails findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> searchByNameUsernameOrEmail(@Param("search") String search);
    // Find all active users with pagination
    Page<User> findAllByActiveTrue(Pageable pageable);

    // Find all users with pagination
    Page<User> findAll(Pageable pageable);

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
