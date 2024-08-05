package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vet.flordelotus.api.domain.entity.Animal;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByOwnerIdAndActiveTrue(Long ownerId);
    Page<Animal> findByActiveTrue(Pageable pageable);
}
