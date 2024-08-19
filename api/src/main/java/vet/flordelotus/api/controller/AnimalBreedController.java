package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedCreateDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedDetailDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedListDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedUpdateDTO;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.repository.AnimalBreedRepository;

import java.util.List;

@RestController
@RequestMapping("/animal-breeds")
@SecurityRequirement(name = "bearer-key")
public class AnimalBreedController {

    @Autowired
    private AnimalBreedRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createAnimalBreed(@RequestBody @Valid AnimalBreedCreateDTO data, UriComponentsBuilder uriBuilder) {
        var animalBreed = new AnimalBreed(data);
        repository.save(animalBreed);

        var uri = uriBuilder.path("/animal-breeds/{id}").buildAndExpand(animalBreed.getId()).toUri();

        return ResponseEntity.created(uri).body(new AnimalBreedDetailDTO(animalBreed));
    }

    @GetMapping
    public List<AnimalBreedListDTO> listAnimalBreeds() {
        return repository.findAll().stream().map(AnimalBreedListDTO::new).toList();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAnimalBreed(@PathVariable Long id, @RequestBody @Valid AnimalBreedUpdateDTO data) {
        var animalBreed = repository.getReferenceById(id);
        animalBreed.updateInformation(data);
        return ResponseEntity.ok(new AnimalBreedDetailDTO(animalBreed));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAnimalBreed(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimalBreed(@PathVariable Long id) {
        var animalBreed = repository.getReferenceById(id);
        return ResponseEntity.ok(new AnimalBreedDetailDTO(animalBreed));
    }
}


