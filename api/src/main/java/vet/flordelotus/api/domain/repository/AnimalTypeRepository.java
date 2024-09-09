package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vet.flordelotus.api.domain.entity.AnimalType;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
}
