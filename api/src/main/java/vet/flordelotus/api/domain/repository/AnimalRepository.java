package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vet.flordelotus.api.domain.entity.Animal;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Page<Animal> findAllByActiveTrue(boolean ActiveTrue, Pageable pageable);

    @Query("SELECT a FROM Animal a " +
            "JOIN a.user u " +
            "JOIN a.type t " +
            "JOIN a.breed b " +
            "WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Animal> searchByNameUsernameTypeOrBreed(@Param("search") String search);
}
