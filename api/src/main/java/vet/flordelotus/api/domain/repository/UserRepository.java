package vet.flordelotus.api.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import vet.flordelotus.api.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //esse metodo realizara a consulta no DB
    UserDetails findByLogin(String login);

}
