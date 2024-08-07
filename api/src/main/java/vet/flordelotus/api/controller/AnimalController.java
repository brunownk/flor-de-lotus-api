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
import vet.flordelotus.api.domain.dto.*;
import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.AnimalRepository;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity createAnimal(@RequestBody @Valid CreateAnimalDTO dados, UriComponentsBuilder uriBuilder) {
        // Buscar Owner pelo ID
        User user = userRepository.findById(dados.userId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Criar Animal
        var animal = new Animal(dados);
        animal.setUser(user);
        repository.save(animal);

        var uri = uriBuilder.path("/animal/{id}").buildAndExpand(animal.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailAnimalDTO(animal));
    }

    @GetMapping
    public List<ListAnimalDTO> listAnimal() {
        return repository.findAll().stream().map(ListAnimalDTO::new).toList();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAnimal(@RequestBody @Valid UpdateAnimalDTO dados) {
        var animal = repository.getReferenceById(dados.id());
        animal.updateInformations(dados);
        return ResponseEntity.ok(new DetailAnimalDTO(animal));
    }

    @DeleteMapping("/{id}") // necessario chaves({}), pois, sem isso o Spring vai considerar que a URL para chamar esse método deve ser /medicos/id, ou seja, ele vai considerar que a palavra id faz parte da URL, e não que se trata de um parâmetro dinâmico.
    @Transactional
    public ResponseEntity deactivateAnimal(@PathVariable Long id){
        var animal = repository.getReferenceById(id); // Recupera o medico do banco de dados
        //Seta atributo pra inativo
        animal.deactivate();
        //Transactional realizara o update
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailAnimal(@PathVariable Long id) {
        var animal = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetailAnimalDTO(animal));
    }
}