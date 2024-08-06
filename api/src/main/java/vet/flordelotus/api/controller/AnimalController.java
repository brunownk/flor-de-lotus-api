package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.CreateAnimalDTO;
import vet.flordelotus.api.domain.dto.DetailAnimalDTO;
import vet.flordelotus.api.domain.dto.ListAnimalDTO;
import vet.flordelotus.api.domain.dto.UpdateAnimalDTO;
import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.service.AnimalService;

@RestController
@RequestMapping("/animals")
@SecurityRequirement(name = "bearer-key")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping
    @Transactional
    public ResponseEntity<DetailAnimalDTO> createAnimal(@RequestBody @Valid CreateAnimalDTO dados, UriComponentsBuilder uriBuilder) {
        Animal animal = animalService.createAnimal(dados);

        var uri = uriBuilder.path("/animals/{id}").buildAndExpand(animal.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailAnimalDTO(animal));
    }

    @GetMapping
    public ResponseEntity<Page<ListAnimalDTO>> listAnimals(Pageable paginacao) {
        Page<Animal> page = animalService.getAllAnimals(paginacao);
        return ResponseEntity.ok(page.map(ListAnimalDTO::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetailAnimalDTO> updateAnimal(@RequestBody @Valid UpdateAnimalDTO dados) {
        Animal animal = animalService.updateAnimal(dados);
        return ResponseEntity.ok(new DetailAnimalDTO(animal));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailAnimalDTO> detailAnimal(@PathVariable Long id) {
        Animal animal = animalService.getAnimalById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
        return ResponseEntity.ok(new DetailAnimalDTO(animal));
    }
}
