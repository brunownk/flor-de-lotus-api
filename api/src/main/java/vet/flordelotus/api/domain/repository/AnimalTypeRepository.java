package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.AnimalType;

import java.util.List;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

        @Query("SELECT a FROM AnimalType a WHERE a.deletedAt IS NULL")
        Page<AnimalType> findAllActive(Pageable pageable);

        @Query("SELECT a FROM AnimalType a WHERE a.deletedAt IS NULL AND a.name LIKE %:name%")
        Page<AnimalType> searchActiveByName(@Param("name") String name, Pageable pageable);

        Page<AnimalType> findAll(Pageable pageable);

        @Query("SELECT a FROM AnimalType a WHERE a.name LIKE %:name%")
        Page<AnimalType> searchByName(@Param("name") String name, Pageable pageable);
    }
