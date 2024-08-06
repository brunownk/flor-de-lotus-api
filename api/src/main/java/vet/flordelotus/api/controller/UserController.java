package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.flordelotus.api.domain.dto.CreateAnimalDTO;
import vet.flordelotus.api.domain.dto.CreateUserDTO;
import vet.flordelotus.api.domain.dto.UpdateAnimalDTO;
import vet.flordelotus.api.domain.dto.UpdateUserDTO;
import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.service.AnimalService;
import vet.flordelotus.api.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AnimalService animalService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO dto) {
        User user = userService.createUser(dto);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDTO dto) {
        User user = userService.updateUser(dto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            log.info("User found: " + user.get());
            return ResponseEntity.ok(user.get());
        } else {
            log.info("User not found for ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
}

    @GetMapping("/{userId}/animals")
    public ResponseEntity<List<Animal>> getUserAnimals(@PathVariable Long userId) {
        return ResponseEntity.ok(animalService.getAnimalsByOwner(userId));
    }

    @PostMapping("/{userId}/animals")
    public ResponseEntity<Animal> addUserAnimal(@PathVariable Long userId, @RequestBody CreateAnimalDTO animalDTO) {
        animalDTO = new CreateAnimalDTO(
                animalDTO.name(),
                userId,
                animalDTO.species(),
                animalDTO.breed(),
                animalDTO.gender(),
                animalDTO.dateOfBirth(),
                animalDTO.createdById()
        );
        return ResponseEntity.ok(animalService.createAnimal(animalDTO));
    }

    @PutMapping("/{userId}/animals/{animalId}")
    public ResponseEntity<Animal> updateUserAnimal(@PathVariable Long userId, @PathVariable Long animalId, @RequestBody UpdateAnimalDTO animalDTO) {
        Animal animal = animalService.getAnimalById(animalId).orElseThrow(() -> new RuntimeException("Animal not found"));
        if (!animal.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        animalDTO = new UpdateAnimalDTO(
                animalDTO.id(),
                animalDTO.name(),
                animalDTO.species(),
                animalDTO.breed(),
                animalDTO.gender(),
                animalDTO.dateOfBirth()
        );
        return ResponseEntity.ok(animalService.updateAnimal(animalDTO));
    }

    @DeleteMapping("/{userId}/animals/{animalId}")
    public ResponseEntity<Void> deleteUserAnimal(@PathVariable Long userId, @PathVariable Long animalId) {
        Animal animal = animalService.getAnimalById(animalId).orElseThrow(() -> new RuntimeException("Animal not found"));
        if (!animal.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        animalService.deleteAnimal(animalId);
        return ResponseEntity.noContent().build();
    }
}
