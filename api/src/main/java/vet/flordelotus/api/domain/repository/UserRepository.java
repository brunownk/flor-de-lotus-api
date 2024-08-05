package vet.flordelotus.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.flordelotus.api.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
