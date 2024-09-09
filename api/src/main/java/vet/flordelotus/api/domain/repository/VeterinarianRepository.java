package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vet.flordelotus.api.domain.entity.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    // Method to find active veterinarians with pagination
    Page<Veterinarian> findByActiveTrue(Pageable pageable);

    // Method to find all veterinarians (including inactive ones) with pagination
    Page<Veterinarian> findAll(Pageable pageable);
}
