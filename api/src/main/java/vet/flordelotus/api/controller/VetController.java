package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.vetDTO.VetCreateDTO;
import vet.flordelotus.api.domain.dto.vetDTO.VetDetailDTO;
import vet.flordelotus.api.domain.dto.vetDTO.VetListDTO;
import vet.flordelotus.api.domain.dto.vetDTO.VetUpdateDTO;
import vet.flordelotus.api.domain.entity.Veterinarian;
import vet.flordelotus.api.domain.repository.VeterinarianRepository;

import java.util.List;

@RestController
@RequestMapping("veterinarians")
@SecurityRequirement(name = "bearer-key")
public class VetController {

    @Autowired
    private VeterinarianRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createVet(@RequestBody @Valid VetCreateDTO data, UriComponentsBuilder uriBuilder) {
        var vet = new Veterinarian(data);
        repository.save(vet);

        var uri = uriBuilder.path("/veterinarians/{id}").buildAndExpand(vet.getId()).toUri();
        return ResponseEntity.created(uri).body(new VetDetailDTO(vet));
    }

    @GetMapping
    public List<VetListDTO> listVet() {
        return repository.findAll().stream().map(VetListDTO::new).toList();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateVet(@RequestBody @Valid VetUpdateDTO data){
        var vet = repository.getReferenceById(data.id());
        vet.updateInformations(data);
        return ResponseEntity.ok(new VetDetailDTO(vet));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deactivateVet(@PathVariable Long id){
        var vet = repository.getReferenceById(id);
        vet.deactivate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailVet(@PathVariable Long id) {
        var vet = repository.getReferenceById(id);
        return ResponseEntity.ok(new VetDetailDTO(vet));
    }
}
