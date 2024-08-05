package vet.flordelotus.api.controller;


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
import vet.flordelotus.api.domain.repository.AnimalRepository;

@RestController
@RequestMapping("animals")
public class AnimalController {

    @Autowired
    private AnimalRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createAnimal(@RequestBody @Valid CreateAnimalDTO dados, UriComponentsBuilder uriBuilder) {
        var animal = new Animal(dados);
        repository.save(animal);

        var uri = uriBuilder.path("/animal/{id}").buildAndExpand(animal.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailAnimalDTO(animal));
    }

    @GetMapping
    public ResponseEntity<Page<ListAnimalDTO>> listAnimals(Pageable paginacao) {
        var page = repository.findAll(paginacao).map(ListAnimalDTO::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAnimal(@RequestBody @Valid UpdateAnimalDTO dados) {
        var animal = repository.getReferenceById(dados.id());
        animal.updateInformations(dados);

        return ResponseEntity.ok(new DetailAnimalDTO(animal));
    }

   @DeleteMapping("/{id}")
   @Transactional
    public ResponseEntity deleteAnimal(@PathVariable Long id) {
        var animal = repository.getReferenceById(id);
        animal.delete();

       return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailAnimal(@PathVariable Long id) {
        var animal = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetailAnimalDTO(animal));
    }
}