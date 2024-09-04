package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vet.flordelotus.api.domain.dto.userDTO.UserCreateDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserUpdateDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Table(name = "users")
@Entity(name = "User")
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

    private String crmv;

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

    public User(UserCreateDTO data) {
        this.active = true;
        this.login = data.login();
        this.password = data.password();
        this.name = data.name();
        this.username = data.username();
        this.crmv = data.crmv();
    }

    public void updateInformations(UserUpdateDTO data) {
        if (data.login() != null) {
            this.login = data.login();
        }
        if (data.password() != null) {
            this.password = data.password();
        }
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.username() != null) {
            this.username = data.username();
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

