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
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeCreateDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeDetailDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeListDTO;
import vet.flordelotus.api.domain.dto.animalTypeDTO.AnimalTypeUpdateDTO;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.domain.repository.AnimalTypeRepository;

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
    public ResponseEntity<Page<AnimalTypeListDTO>> listAnimalTypes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<AnimalType> types;

        types = repository.findAll(pageable);

        Page<AnimalTypeListDTO> typeDTOs = types.map(AnimalTypeListDTO::new);
        return ResponseEntity.ok(typeDTOs);
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
        var animalType = repository.getReferenceById(id);
        repository.save(animalType);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimalType(@PathVariable Long id) {
        var animalType = repository.getReferenceById(id);
        return ResponseEntity.ok(new AnimalTypeDetailDTO(animalType));
    }
}
