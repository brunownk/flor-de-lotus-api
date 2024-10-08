package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.userDTO.ChangePasswordDTO;
import vet.flordelotus.api.enums.role.Role;
import vet.flordelotus.api.infra.exception.ExceptionValidation;
import vet.flordelotus.api.domain.dto.animalDTO.AnimalDetailDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserCreateDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserDetailDTO;
import vet.flordelotus.api.domain.dto.userDTO.UserUpdateDTO;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailDTO> createUser(@RequestBody @Valid UserCreateDTO data, UriComponentsBuilder uriBuilder) {

        UserDetails existingUserByUsername = repository.findByUsername(data.username());
        if (existingUserByUsername != null) {
            throw new ExceptionValidation("Username already exists.");
        }

        UserDetails existingUserByEmail = repository.findByEmail(data.email());
        if (existingUserByEmail != null) {
            throw new ExceptionValidation("Email already exists.");
        }


        String encodedPassword = passwordEncoder.encode(data.password());

        var user = new User(data);
        user.setPassword(encodedPassword);

        if (data.role() == null) {
            user.setRole(Role.USER);
        } else {
            user.setRole(data.role());
        }

        repository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserDetailDTO>> listUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "false") boolean withDeleted,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<User> users;

        if (search != null && !search.isEmpty()) {
            if (withDeleted) {
                users = repository.searchByNameUsernameOrEmail(search, pageable);
            } else {
                users = repository.searchByNameUsernameOrEmailAndActiveTrue(search, pageable);
            }
        } else {
            if (withDeleted) {
                users = repository.findAll(pageable);
            } else {
                users = repository.findAllByActiveTrue(pageable);
            }
        }

        Page<UserDetailDTO> userDTOs = users.map(UserDetailDTO::new);
        return ResponseEntity.ok(userDTOs);
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
    public ResponseEntity deactivateUser(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        user.deactivate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailUser(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        return ResponseEntity.ok(new UserDetailDTO(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailDTO> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();

        UserDetails userDetails = repository.findByUsername(username);

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = (User) userDetails;

        UserDetailDTO userDetailDTO = new UserDetailDTO(user);

        return ResponseEntity.ok(userDetailDTO);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (passwordEncoder.matches(changePasswordDTO.oldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
                repository.save(user);
                return ResponseEntity.ok("Password changed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
