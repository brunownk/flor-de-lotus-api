package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import vet.flordelotus.api.domain.animal.Gender;
import vet.flordelotus.api.domain.animal.Species;
import vet.flordelotus.api.domain.dto.CreateAnimalDTO;
import vet.flordelotus.api.domain.dto.UpdateAnimalDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "animals")
@Entity(name = "Animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "species", nullable = false)
    private Species species;

    @Size(max = 100)
    @Column(name = "breed")
    private String breed;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @NotNull
    @Column(name = "created_by_id", nullable = false, updatable = false)
    private Long createdById;

    @Column(name = "deleted_by_id")
    private Long deletedById;

    private Boolean active ;

    public Animal(CreateAnimalDTO dados) {
        this.active = true;
        this.name = dados.name();
        this.species = dados.species();
        this.breed = dados.breed();
        this.gender = dados.gender();
        this.dateOfBirth = dados.dateOfBirth();
        this.createdById = dados.createdById();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void updateInformations(UpdateAnimalDTO dados) {
        if (dados.name() != null) {
            this.name = dados.name();
        }
        if (dados.species() != null) {
            this.species = dados.species();
        }
        if (dados.breed() != null) {
            this.breed = dados.breed();
        }
        if (dados.gender() != null) {
            this.gender = dados.gender();
        }
        if (dados.dateOfBirth() != null) {
            this.dateOfBirth = dados.dateOfBirth();
        }
    }

    public void delete() {
        this.active = false;
        this.deletedAt = LocalDateTime.now();
    }
}
