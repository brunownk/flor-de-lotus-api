package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import vet.flordelotus.api.enums.animal.Gender;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalCreateDTO;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalUpdateDTO;

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
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_type_id", nullable = false)
    private AnimalType type;

    @ManyToOne
    @JoinColumn(name = "animal_breed_id", nullable = false)
    private AnimalBreed breed;

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

    private Boolean active;

    public Animal(AnimalCreateDTO data) {
        this.active = true;
        this.name = data.name();
        this.user = getUser();
        this.breed = new AnimalBreed(data.animalBreedId());
        this.gender = data.gender();
        this.dateOfBirth = data.dateOfBirth();
        this.createdById = data.createdById();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void updateInformations(AnimalUpdateDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.breed() != null) {
            this.breed = data.breed();
        }
        if (data.type() != null) {
            this.type = data.type();
        }
        if (data.gender() != null) {
            this.gender = data.gender();
        }
        if (data.dateOfBirth() != null) {
            this.dateOfBirth = data.dateOfBirth();
        }
    }

    public void delete() {
        this.active = false;
        this.deletedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
    }

}

