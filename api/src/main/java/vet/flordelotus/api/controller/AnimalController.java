package vet.flordelotus.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalCreateDTO;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalDetailDTO;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalListDTO;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalUpdateDTO;
import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.AnimalRepository;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/animals")
@SecurityRequirement(name = "bearer-key")
public class AnimalController {

    @Autowired
    private AnimalRepository repository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity createAnimal(@RequestBody @Valid AnimalCreateDTO data, UriComponentsBuilder uriBuilder) {
        User user = userRepository.findById(data.userId()).orElseThrow(() -> new RuntimeException("User not found"));

        var animal = new Animal(data);
        animal.setUser(user);
        repository.save(animal);

        var uri = uriBuilder.path("/animal/{id}").buildAndExpand(animal.getId()).toUri();

        return ResponseEntity.created(uri).body(new AnimalDetailDTO(animal));
    }

    @GetMapping
    public List<AnimalListDTO> listAnimals() {
        return repository.findAll().stream().map(AnimalListDTO::new).toList();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAnimal(@RequestBody @Valid AnimalUpdateDTO data) {
        var animal = repository.getReferenceById(data.id());
        animal.updateInformations(data);
        return ResponseEntity.ok(new AnimalDetailDTO(animal));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deactivateAnimal(@PathVariable Long id){
        var animal = repository.getReferenceById(id);
        animal.deactivate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimal(@PathVariable Long id) {
        var animal = repository.getReferenceById(id);
        return ResponseEntity.ok(new AnimalDetailDTO(animal));
    }
}
