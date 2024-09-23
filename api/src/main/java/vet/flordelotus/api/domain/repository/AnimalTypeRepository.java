package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.AnimalType;

import java.util.List;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

    @Query("SELECT a FROM AnimalType a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<AnimalType> searchByName(@Param("search") String search, Pageable pageable);

}
