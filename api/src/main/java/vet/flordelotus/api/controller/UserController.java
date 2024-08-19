package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalDetailDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserCreateDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserDetailDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserListDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserUpdateDTO;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createUser(@RequestBody @Valid UserCreateDTO data, UriComponentsBuilder uriBuilder) {
        var user = new User(data);
        repository.save(user);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailDTO(user));
    }

    @GetMapping
    public List<UserListDTO> listUsers() {
        return repository.findAll().stream().map(UserListDTO::new).toList();
    }

    @GetMapping("/{id}/animals")
    public ResponseEntity<List<AnimalDetailDTO>> listAnimalsByUser(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<AnimalDetailDTO> animals = user.getAnimals().stream()
                .map(AnimalDetailDTO::new)
                .toList();

        return ResponseEntity.ok(animals);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateUser(@RequestBody @Valid UserUpdateDTO data) {
        var user = repository.getReferenceById(data.id());
        user.updateInformations(data);
        return ResponseEntity.ok(new UserDetailDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deactivateUser(@PathVariable Long id){
        var user = repository.getReferenceById(id);
        user.deactivate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailUser(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        return ResponseEntity.ok(new UserDetailDTO(user));
    }
}
