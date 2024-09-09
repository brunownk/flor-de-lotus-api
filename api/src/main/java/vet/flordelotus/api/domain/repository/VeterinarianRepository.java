package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.entity.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    // Method to find active veterinarians with pagination
    @Query("SELECT v FROM Veterinarian v WHERE v.user.active = true")
    Page<Veterinarian> findAllByActiveTrue(Pageable pageable);
    // Method to find all veterinarians (including inactive ones) with pagination
    Page<Veterinarian> findAll(Pageable pageable);
}
