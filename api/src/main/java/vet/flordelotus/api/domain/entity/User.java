package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vet.flordelotus.api.domain.dto.userDTO.UserCreateDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserUpdateDTO;
import vet.flordelotus.api.enums.role.Role;

import java.time.LocalDateTime;
import java.util.*;

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

    private String username;
    private String password;
    private String name;
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "user")
    private List<Animal> animals;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public User(UserCreateDTO data) {
        this.active = true;
        this.username = data.username();
        this.password = data.password();
        this.name = data.name();
        this.email = data.email();
        this.role = data.role();
        this.createdAt = LocalDateTime.now();
    }

    public void updateInformations(UserUpdateDTO data) {
        if (data.username() != null) {
            this.username = data.username();
        }
        if (data.password() != null) {
            this.password = data.password();
        }
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.email() != null) {
            this.email = data.email();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return Boolean.TRUE.equals(active);
    }

    public void deactivate() {
        this.deletedAt = LocalDateTime.now();
        this.active = false;
    }
}
