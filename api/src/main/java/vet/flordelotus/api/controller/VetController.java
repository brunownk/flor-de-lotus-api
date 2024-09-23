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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.userDTO.UserDetailDTO;
import vet.flordelotus.api.domain.dto.vetDTO.VetCreateDTO;
import vet.flordelotus.api.domain.dto.vetDTO.VetDetailDTO;
import vet.flordelotus.api.domain.dto.vetDTO.VetListDTO;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.domain.repository.UserRepository;
import vet.flordelotus.api.domain.repository.VeterinarianRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("veterinarians")
@SecurityRequirement(name = "bearer-key")
public class VetController {

    @Autowired
    private VeterinarianRepository repository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity createVet(@RequestBody @Valid VetCreateDTO data, UriComponentsBuilder uriBuilder) {

        User user = userRepository.findById(data.userId()).orElseThrow(() -> new RuntimeException("User not found"));

        var vet = new Veterinarian(data);
        vet.setUser(user);
        repository.save(vet);

        var uri = uriBuilder.path("/veterinarians/{id}").buildAndExpand(vet.getId()).toUri();
        return ResponseEntity.created(uri).body(new VetDetailDTO(vet));
    }

    @GetMapping
    public ResponseEntity<Page<VetListDTO>> listVet(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean withDeleted) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Veterinarian> vets;

        if (withDeleted) {
            vets = repository.findAll(pageable); // Retrieves all veterinarians, including inactive ones
        } else {
            vets = repository.findAllByActiveTrue(pageable); // Retrieves only active veterinarians
        }

        Page<VetListDTO> vetDTOs = vets.map(VetListDTO::new);
        return ResponseEntity.ok(vetDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity detailVet(@PathVariable Long id) {
        var vet = repository.getReferenceById(id);
        return ResponseEntity.ok(new VetDetailDTO(vet));
    }

    @GetMapping("/search")
    public ResponseEntity<List<VetDetailDTO>> search(@RequestParam String search) {
        List<Veterinarian> veterinariansList = repository.searchByCrmvUsernameOrEmail(search);

        if (veterinariansList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no veterinarians found
        }

        List<VetDetailDTO> veterinarianDetailDTOs = veterinariansList.stream()
                .map(veterinarian -> new VetDetailDTO(veterinarian)) // Assuming VeterinarianDetailDTO takes a Veterinarian object
                .collect(Collectors.toList());

        return ResponseEntity.ok(veterinarianDetailDTOs); // Return 200 OK with the veterinarian detail DTOs
    }
}

