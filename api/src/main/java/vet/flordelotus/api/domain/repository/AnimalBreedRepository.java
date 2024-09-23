package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.AnimalBreed;

import java.util.List;

public interface AnimalBreedRepository extends JpaRepository<AnimalBreed, Long> {

    @Query("SELECT b FROM AnimalBreed b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<AnimalBreed> searchByName(@Param("search") String search, Pageable pageable);
}
