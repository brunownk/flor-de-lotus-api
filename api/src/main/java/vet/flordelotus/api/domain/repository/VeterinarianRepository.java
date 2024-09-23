package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.entity.Veterinarian;

import java.util.List;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    // Method to find active veterinarians with pagination
    @Query("SELECT v FROM Veterinarian v WHERE v.user.active = true")
    Page<Veterinarian> findAllByActiveTrue(Pageable pageable);

    // Method to find all veterinarians (including inactive ones) with pagination
    Page<Veterinarian> findAll(Pageable pageable);

    @Query("SELECT v FROM Veterinarian v " +
            "WHERE (LOWER(v.crmv) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.user.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.user.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.user.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Veterinarian> searchByCrmvUsernameOrEmail(@Param("search") String search);
}