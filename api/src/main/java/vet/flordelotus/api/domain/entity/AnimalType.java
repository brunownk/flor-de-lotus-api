package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeCreateDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeUpdateDTO;

import java.time.LocalDateTime;

@Entity
@Table(name = "animal_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AnimalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

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

    public AnimalType(AnimalTypeCreateDTO data) {
        this.name = data.name();
        this.createdById = data.createdById();
        this.active = true; // Mark as active by default
    }

    public AnimalType(AnimalTypeDTO data) {
        this.id = data.id();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void updateInformation(AnimalTypeUpdateDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }
    }

    // Method to deactivate an AnimalType
    public void deactivate() {
        this.deletedAt = LocalDateTime.now();
        this.active = false;
    }
}
