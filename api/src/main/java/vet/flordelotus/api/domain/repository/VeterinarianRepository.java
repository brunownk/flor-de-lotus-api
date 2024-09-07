package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vet.flordelotus.api.enums.vet.Specialty;
import vet.flordelotus.api.domain.entity.Veterinarian;

import java.time.LocalDateTime;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

}
