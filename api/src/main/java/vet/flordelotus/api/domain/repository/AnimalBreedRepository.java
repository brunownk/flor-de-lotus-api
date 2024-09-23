package vet.flordelotus.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.AnimalBreed;

import java.util.List;

public interface AnimalBreedRepository extends JpaRepository<AnimalBreed, Long> {

    @Query("SELECT ab FROM AnimalBreed ab WHERE LOWER(ab.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<AnimalBreed> searchByName(@Param("search") String search);
}

