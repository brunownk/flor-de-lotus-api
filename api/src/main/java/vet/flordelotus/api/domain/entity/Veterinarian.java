package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import vet.flordelotus.api.enums.vet.Specialty;
import vet.flordelotus.api.domain.dto.vetDTO.VetCreateDTO;

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

    public Veterinarian(VetCreateDTO data) {
        this.user = getUser();
        this.crmv = data.crmv();
        this.specialty = data.specialty();
    }
}