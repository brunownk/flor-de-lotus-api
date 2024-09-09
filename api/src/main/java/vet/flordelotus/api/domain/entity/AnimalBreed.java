package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedCreateDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedListDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedUpdateDTO;

import java.time.LocalDateTime;

@Entity
@Table(name = "animal_breeds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AnimalBreed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "animal_type_id", nullable = false)
    private AnimalType animalType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by_id", nullable = false, updatable = false)
    private Long createdById;

    @Column(name = "deleted_by_id")
    private Long deletedById;

    @Column(name = "active", nullable = false)
    private Boolean active = true; // Default to true

    public AnimalBreed(AnimalBreedDTO data) {
        this.id = data.id();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public AnimalBreed(AnimalBreedCreateDTO data) {
        this.name = data.name();
        this.animalType = new AnimalType(data.animalTypeId());
        this.createdById = data.createdById();
        this.active = true; // Mark as active by default
    }

    public void updateInformation(AnimalBreedUpdateDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }
    }

    // Method to deactivate an AnimalBreed
    public void deactivate() {
        this.deletedAt = LocalDateTime.now();
        this.active = false;
    }
}
