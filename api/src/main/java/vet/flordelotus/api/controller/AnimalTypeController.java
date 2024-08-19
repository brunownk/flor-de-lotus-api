package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeCreateDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeDetailDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeListDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeUpdateDTO;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.domain.repository.AnimalTypeRepository;

import java.util.List;

@RestController
@RequestMapping("/animal-types")
@SecurityRequirement(name = "bearer-key")
public class AnimalTypeController {

    @Autowired
    private AnimalTypeRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createAnimalType(@RequestBody @Valid AnimalTypeCreateDTO data, UriComponentsBuilder uriBuilder) {
        var animalType = new AnimalType(data);
        repository.save(animalType);

        var uri = uriBuilder.path("/animal-types/{id}").buildAndExpand(animalType.getId()).toUri();

        return ResponseEntity.created(uri).body(new AnimalTypeDetailDTO(animalType));
    }

    @GetMapping
    public List<AnimalTypeListDTO> listAnimalTypes() {
        return repository.findAll().stream().map(AnimalTypeListDTO::new).toList();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAnimalType(@PathVariable Long id, @RequestBody @Valid AnimalTypeUpdateDTO data) {
        var animalType = repository.getReferenceById(id);
        animalType.updateInformation(data);
        return ResponseEntity.ok(new AnimalTypeDetailDTO(animalType));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAnimalType(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimalType(@PathVariable Long id) {
        var animalType = repository.getReferenceById(id);
        return ResponseEntity.ok(new AnimalTypeDetailDTO(animalType));
    }
}



