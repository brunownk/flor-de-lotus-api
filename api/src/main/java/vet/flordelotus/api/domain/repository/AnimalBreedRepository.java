package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.AnimalBreed;

import java.util.List;

public interface AnimalBreedRepository extends JpaRepository<AnimalBreed, Long> {

    @Query("SELECT a FROM AnimalBreed a WHERE a.deletedAt IS NULL")
    Page<AnimalBreed> findAllActive(Pageable pageable);

    @Query("SELECT a FROM AnimalBreed a WHERE a.deletedAt IS NULL AND a.name LIKE %:name%")
    Page<AnimalBreed> searchActiveByName(@Param("name") String name, Pageable pageable);

    Page<AnimalBreed> findAll(Pageable pageable);

    @Query("SELECT a FROM AnimalBreed a WHERE a.name LIKE %:name%")
    Page<AnimalBreed> searchByName(@Param("name") String name, Pageable pageable);
}