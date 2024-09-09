package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import vet.flordelotus.api.enums.vet.Specialty;
import vet.flordelotus.api.domain.dto.vetDTO.VetCreateDTO;

import java.time.LocalDateTime;

@Table(name = "veterinarians")
@Entity(name = "Veterinarian")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Veterinarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crmv;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Column(name = "active", nullable = false)
    private Boolean active = true; // Default to true

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Veterinarian(VetCreateDTO data) {
        this.user = getUser();
        this.crmv = data.crmv();
        this.specialty = data.specialty();
        this.active = true; // Mark as active by default
    }

    // Method to deactivate a veterinarian
    public void deactivate() {
        this.active = false;
        this.deletedAt = LocalDateTime.now();
    }

    // Utility method to check if the veterinarian is active
    public boolean isActive() {
        return Boolean.TRUE.equals(this.active);
    }
}
