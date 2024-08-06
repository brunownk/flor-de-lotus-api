package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.CreateUserDTO;
import vet.flordelotus.api.domain.dto.DetailAnimalDTO;
import vet.flordelotus.api.domain.dto.DetailUserDTO;
import vet.flordelotus.api.domain.dto.ListUserDTO;
import vet.flordelotus.api.domain.dto.UpdateUserDTO;
import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("user")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO dados, UriComponentsBuilder uriBuilder) {
        var user = new User(dados);
        repository.save(user);

        var uri = uriBuilder.path("/owners/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> listUsers(Pageable paginacao) {
        var page = repository.findAll(paginacao).map(ListUserDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}/animals")
    public ResponseEntity<List<DetailAnimalDTO>> listAnimalsByUser(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<DetailAnimalDTO> animals = user.getAnimals().stream()
                .map(DetailAnimalDTO::new)
                .toList();

        return ResponseEntity.ok(animals);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO dados) {
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        user.updateInformations(dados);

        return ResponseEntity.ok(new DetailUserDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        var owner = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        repository.delete(owner);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailUser(@PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return ResponseEntity.ok(new DetailUserDTO(user));
    }

    public List<Animal> getAnimalsByOwner(Long ownerId) {
        User user = repository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return user.getAnimals(); // Obtém a lista de animais associados ao proprietário
    }
}


