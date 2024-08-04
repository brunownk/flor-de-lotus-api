package vet.flordelotus.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.flordelotus.api.domain.animal.AnimalTypeRepository;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animal-types")
public class AnimalTypeController {

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    // Listar todos os tipos de animais
    @GetMapping
    public List<DadosListagemAnimalType> getAllAnimalTypes() {
        return animalTypeRepository.findAll().stream()
                .map(this::toDadosListagem)
                .collect(Collectors.toList());
    }

    // Obter um tipo de animal por ID
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAnimalType> getAnimalTypeById(@PathVariable Long id) {
        return animalTypeRepository.findById(id)
                .map(this::toDadosDetalhamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo tipo de animal
    @PostMapping
    public ResponseEntity<DadosDetalhamentoAnimalType> createAnimalType(@RequestBody @Valid DadosCadastroAnimalType dadosCadastro) {
        AnimalType type = toEntity(dadosCadastro);
        AnimalType savedType = animalTypeRepository.save(type);
        return ResponseEntity.ok(toDadosDetalhamento(savedType));
    }

    // Atualizar um tipo de animal existente
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAnimalType> updateAnimalType(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoAnimalType dadosAtualizacao) {
        return animalTypeRepository.findById(id)
                .map(existingType -> {
                    existingType.setName(dadosAtualizacao.name());
                    existingType.setDeletedAt(dadosAtualizacao.deletedAt());
                    existingType.setDeletedById(dadosAtualizacao.deletedById());
                    AnimalType updatedType = animalTypeRepository.save(existingType);
                    return ResponseEntity.ok(toDadosDetalhamento(updatedType));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar um tipo de animal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimalType(@PathVariable Long id) {
        return animalTypeRepository.findById(id)
                .map(type -> {
                    animalTypeRepository.delete(type);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Conversão de entidade para DTO de listagem
    private DadosListagemAnimalType toDadosListagem(AnimalType type) {
        return new DadosListagemAnimalType(
                type.getId(),
                type.getName()
        );
    }

    // Conversão de entidade para DTO de detalhamento
    private DadosDetalhamentoAnimalType toDadosDetalhamento(AnimalType type) {
        return new DadosDetalhamentoAnimalType(
                type.getId(),
                type.getName(),
                type.getCreatedAt(),
                type.getDeletedAt(),
                type.getCreatedById(),
                type.getDeletedById()
        );
    }

    // Conversão de DTO de cadastro para entidade
    private AnimalType toEntity(DadosCadastroAnimalType dadosCadastro) {
        AnimalType type = new AnimalType();
        type.setName(dadosCadastro.name());
        type.setCreatedById(dadosCadastro.createdById());
        type.setCreatedAt(LocalDateTime.now());
        return type;
    }
}
