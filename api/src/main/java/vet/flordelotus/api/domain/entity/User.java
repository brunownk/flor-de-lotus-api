package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vet.flordelotus.api.domain.dto.CreateUserDTO;
import vet.flordelotus.api.domain.dto.UpdateUserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Entidade JPA

//Essa entidade se chama Medicos no DB
@Table(name = "users")
@Entity(name = "User")
//Anotacoes LOMBOK para criar getters/setters e construtores automaticamente
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animals = new ArrayList<>();

    public User(CreateUserDTO dados) {
        this.login = dados.login();
        this.password = dados.password();
    }

    public void updateInformations(UpdateUserDTO dados) {
        if (dados.login() != null) {
            this.login = dados.login();
        }
        if (dados.password() != null) {
            this.password = dados.password();
        }
    }

    @Override
    //Controle de perfis
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
