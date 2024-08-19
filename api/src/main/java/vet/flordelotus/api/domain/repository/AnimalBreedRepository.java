package vet.flordelotus.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.flordelotus.api.domain.entity.AnimalBreed;

public interface AnimalBreedRepository extends JpaRepository<AnimalBreed, Long> {
}

