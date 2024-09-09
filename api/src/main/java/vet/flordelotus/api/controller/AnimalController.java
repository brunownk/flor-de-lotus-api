package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<AnimalDetailDTO> createAnimal(@RequestBody @Valid AnimalCreateDTO data, UriComponentsBuilder uriBuilder) {
        User user = userRepository.findById(data.userId()).orElseThrow(() -> new RuntimeException("User not found"));

        var animal = new Animal(data);
        animal.setUser(user);
        repository.save(animal);

        var uri = uriBuilder.path("/animals/{id}").buildAndExpand(animal.getId()).toUri();

        return ResponseEntity.created(uri).body(new AnimalDetailDTO(animal));
    }

    @GetMapping
    public ResponseEntity<Page<AnimalListDTO>> listAnimals(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean withDeleted) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Animal> animals;

        if (withDeleted) {
            animals = repository.findAll(pageable); // Retrieves all animals, including inactive ones
        } else {
            animals = repository.findByIsActive(true, pageable); // Retrieves only active animals
        }

        Page<AnimalListDTO> animalDTOs = animals.map(AnimalListDTO::new);
        return ResponseEntity.ok(animalDTOs);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AnimalDetailDTO> updateAnimal(@RequestBody @Valid AnimalUpdateDTO data) {
        var animal = repository.getReferenceById(data.id());
        animal.updateInformations(data);
        return ResponseEntity.ok(new AnimalDetailDTO(animal));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deactivateAnimal(@PathVariable Long id) {
        var animal = repository.getReferenceById(id);
        animal.deactivate();  // Assuming 'deactivate' is a method that marks the animal as deleted
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetailDTO> getAnimal(@PathVariable Long id) {
        var animal = repository.getReferenceById(id);
        return ResponseEntity.ok(new AnimalDetailDTO(animal));
    }
}
