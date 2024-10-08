package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedCreateDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedDetailDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedListDTO;
import vet.flordelotus.api.domain.dto.animalBreedDTO.AnimalBreedUpdateDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserDetailDTO;
import vet.flordelotus.api.domain.entity.AnimalBreed;
import vet.flordelotus.api.domain.entity.AnimalType;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.AnimalBreedRepository;
import vet.flordelotus.api.domain.repository.AnimalTypeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animal-breeds")
@SecurityRequirement(name = "bearer-key")
public class AnimalBreedController {

    @Autowired
    private AnimalBreedRepository repository;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<AnimalBreedDetailDTO> createAnimalBreed(@RequestBody @Valid AnimalBreedCreateDTO data, UriComponentsBuilder uriBuilder) {
        // Verifica se o tipo de animal (animalTypeId) foi fornecido
        if (data.animalTypeId() != null) {
            // Busca o AnimalType pelo ID
            AnimalType animalType = animalTypeRepository.findById(data.animalTypeId())
                    .orElseThrow(() -> new RuntimeException("Animal type not found"));

            // Cria uma nova instância de AnimalBreed com o AnimalType associado
            var animalBreed = new AnimalBreed(data);
            animalBreed.setAnimalType(animalType); // Associa o tipo de animal

            // Salva a nova raça no repositório
            repository.save(animalBreed);

            // Gera a URI para o novo recurso
            var uri = uriBuilder.path("/animal-breeds/{id}").buildAndExpand(animalBreed.getId()).toUri();

            // Retorna a resposta com o novo AnimalBreed
            return ResponseEntity.created(uri).body(new AnimalBreedDetailDTO(animalBreed));
        }

        throw new RuntimeException("Animal type ID is required."); // Tratamento para o caso de tipo não fornecido
    }

    @GetMapping
    public ResponseEntity<Page<AnimalBreedDetailDTO>> listAnimalBreeds(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "false") boolean withDeleted) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<AnimalBreed> breeds;

        if (search != null && !search.isEmpty()) {
            if (withDeleted) {
                // Se withDeleted for true, busca incluindo os inativos
                breeds = repository.searchByName(search, pageable);
            } else {
                // Se withDeleted for false, busca apenas os ativos
                breeds = repository.searchActiveByName(search, pageable);
            }
        } else {
            if (withDeleted) {
                // Retorna todos os registros, incluindo os inativos
                breeds = repository.findAll(pageable);
            } else {
                // Retorna apenas os registros ativos (deletedAt == null)
                breeds = repository.findAllActive(pageable);
            }
        }

        Page<AnimalBreedDetailDTO> breedDTOs = breeds.map(AnimalBreedDetailDTO::new);
        return ResponseEntity.ok(breedDTOs);
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
        var animalBreed = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal breed not found"));
        animalBreed.setDeletedAt(LocalDateTime.now());
        repository.save(animalBreed);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimalBreed(@PathVariable Long id) {
        var animalBreed = repository.getReferenceById(id);
        return ResponseEntity.ok(new AnimalBreedDetailDTO(animalBreed));
    }
}
