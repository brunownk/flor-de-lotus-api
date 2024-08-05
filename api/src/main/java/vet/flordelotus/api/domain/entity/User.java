package vet.flordelotus.api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "User")
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String role;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "owner")
    private List<Animal> animals;

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}
