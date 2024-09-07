package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vet.flordelotus.api.enums.vet.Specialty;
import vet.flordelotus.api.domain.dto.vetDTO.VetCreateDTO;
import vet.flordelotus.api.domain.dto.vetDTO.VetUpdateDTO;

//Entidade JPA

//Essa entidade se chama Medicos no DB
@Table(name = "veterinarians")
@Entity(name = "Veterinarian")
//Anotacoes LOMBOK para criar getters/setters e construtores automaticamente
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //Gera o hash em cima do ID
public class Veterinarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String crmv;
    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    public Veterinarian(VetCreateDTO data) {
        this.active = true;
        this.name = data.name();
        this.username = data.username();
        this.crmv = data.crmv();
        this.specialty = data.specialty();
    }

    public void updateInformations(VetUpdateDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.username() != null) {
            this.username = data.username();
        }
    }

    public void deactivate() {
        this.active = false;
    }
}
