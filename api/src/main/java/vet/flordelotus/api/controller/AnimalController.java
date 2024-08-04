package vet.flordelotus.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.flordelotus.api.domain.animal.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalBreedRepository animalBreedRepository;

    @GetMapping
    public List<DadosListagemAnimal> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(this::toDadosListagem)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAnimal> getAnimalById(@PathVariable Long id) {
        return animalRepository.findById(id)
                .map(this::toDadosDetalhamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoAnimal> createAnimal(@RequestBody @Valid DadosCadastroAnimal dadosCadastro) {
        Animal animal = toEntity(dadosCadastro);
        Animal savedAnimal = animalRepository.save(animal);
        return ResponseEntity.ok(toDadosDetalhamento(savedAnimal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAnimal> updateAnimal(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoAnimal dadosAtualizacao) {
        return animalRepository.findById(id)
                .map(existingAnimal -> {
                    existingAnimal.setName(dadosAtualizacao.name());
                    existingAnimal.setBreed(animalBreedRepository.findById(dadosAtualizacao.breedId()).orElse(null));
                    existingAnimal.setOwnerId(dadosAtualizacao.ownerId());
                    existingAnimal.setDeletedAt(dadosAtualizacao.deletedAt());
                    existingAnimal.setDeletedById(dadosAtualizacao.deletedById());
                    Animal updatedAnimal = animalRepository.save(existingAnimal);
                    return ResponseEntity.ok(toDadosDetalhamento(updatedAnimal));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnimal(@PathVariable Long id) {
        return animalRepository.findById(id)
                .map(animal -> {
                    animalRepository.delete(animal);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private DadosListagemAnimal toDadosListagem(Animal animal) {
        String breedName = animal.getBreed() != null ? animal.getBreed().getName() : null;
        return new DadosListagemAnimal(
                animal.getId(),
                animal.getName(),
                breedName
        );
    }

    private DadosDetalhamentoAnimal toDadosDetalhamento(Animal animal) {
        return new DadosDetalhamentoAnimal(
                animal.getId(),
                animal.getName(),
                animal.getBreed() != null ? animal.getBreed().getId() : null,
                animal.getOwnerId(),
                animal.getCreatedAt(),
                animal.getDeletedAt(),
                animal.getCreatedById(),
                animal.getDeletedById()
        );
    }

    private Animal toEntity(DadosCadastroAnimal dadosCadastro) {
        Animal animal = new Animal();
        animal.setName(dadosCadastro.name());
        animal.setBreed(animalBreedRepository.findById(dadosCadastro.breedId()).orElse(null));
        animal.setOwnerId(dadosCadastro.ownerId());
        animal.setCreatedById(dadosCadastro.createdById());
        animal.setCreatedAt(LocalDateTime.now());
        return animal;
    }
}
