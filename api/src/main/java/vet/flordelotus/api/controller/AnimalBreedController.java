package vet.flordelotus.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animal-breeds")
public class AnimalBreedController {

    @Autowired
    private AnimalBreedRepository animalBreedRepository;

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    @GetMapping
    public List<DadosListagemAnimalBreed> getAllAnimalBreeds() {
        return animalBreedRepository.findAll().stream()
                .map(this::toDadosListagem)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAnimalBreed> getAnimalBreedById(@PathVariable Long id) {
        return animalBreedRepository.findById(id)
                .map(this::toDadosDetalhamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoAnimalBreed> createAnimalBreed(@RequestBody @Valid DadosCadastroAnimalBreed dadosCadastro) {
        AnimalBreed breed = toEntity(dadosCadastro);
        AnimalBreed savedBreed = animalBreedRepository.save(breed);
        return ResponseEntity.ok(toDadosDetalhamento(savedBreed));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAnimalBreed> updateAnimalBreed(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoAnimalBreed dadosAtualizacao) {
        return animalBreedRepository.findById(id)
                .map(existingBreed -> {
                    existingBreed.setName(dadosAtualizacao.name());
                    existingBreed.setType(animalTypeRepository.findById(dadosAtualizacao.typeId()).orElse(null));
                    existingBreed.setDeletedAt(dadosAtualizacao.deletedAt());
                    existingBreed.setDeletedById(dadosAtualizacao.deletedById());
                    AnimalBreed updatedBreed = animalBreedRepository.save(existingBreed);
                    return ResponseEntity.ok(toDadosDetalhamento(updatedBreed));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimalBreed(@PathVariable Long id) {
        return animalBreedRepository.findById(id)
                .map(breed -> {
                    animalBreedRepository.delete(breed);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private DadosListagemAnimalBreed toDadosListagem(AnimalBreed breed) {
        String typeName = breed.getType() != null ? breed.getType().getName() : null;
        return new DadosListagemAnimalBreed(
                breed.getId(),
                breed.getName(),
                typeName
        );
    }

    private DadosDetalhamentoAnimalBreed toDadosDetalhamento(AnimalBreed breed) {
        return new DadosDetalhamentoAnimalBreed(
                breed.getId(),
                breed.getName(),
                breed.getType() != null ? breed.getType().getId() : null,
                breed.getCreatedAt(),
                breed.getDeletedAt(),
                breed.getCreatedById(),
                breed.getDeletedById()
        );
    }

    private AnimalBreed toEntity(DadosCadastroAnimalBreed dadosCadastro) {
        AnimalBreed breed = new AnimalBreed();
        breed.setName(dadosCadastro.name());
        breed.setType(animalTypeRepository.findById(dadosCadastro.typeId()).orElse(null));
        breed.setCreatedById(dadosCadastro.createdById());
        breed.setCreatedAt(LocalDateTime.now());
        return breed;
    }
}
