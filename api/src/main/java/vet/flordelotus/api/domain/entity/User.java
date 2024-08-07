package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vet.flordelotus.api.domain.dto.CreateUserDTO;
import vet.flordelotus.api.domain.dto.UpdateUserDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


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

    private String name;
    private String username;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Boolean active;

    @OneToMany(mappedBy = "user")
    private List<Animal> animals;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(CreateUserDTO dados) {
        this.active = true;
        this.login = dados.login();
        this.password = dados.password();
        this.name = dados.name();
        this.username = dados.username();
    }

    public void updateInformations(UpdateUserDTO dados) {
        if (dados.login() != null) {
            this.login = dados.login();
        }
        if (dados.password() != null) {
            this.password = dados.password();
        }
        if (dados.name() != null) {
            this.name = dados.name();
        }
        if (dados.username() != null) {
            this.username = dados.username();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
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

    public void deactivate() {
        this.active = false;
    }

}
