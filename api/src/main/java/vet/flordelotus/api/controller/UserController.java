package vet.flordelotus.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vet.flordelotus.api.domain.dto.*;
import vet.flordelotus.api.domain.entity.Animal;
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
    public ResponseEntity createUser(@RequestBody @Valid CreateUserDTO dados, UriComponentsBuilder uriBuilder) {
        var user = new User(dados);
        repository.save(user);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailUserDTO(user));
    }

    @GetMapping
    public List<ListUserDTO> listUserl() {
        return repository.findAll().stream().map(ListUserDTO::new).toList();
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

    @PutMapping
    @Transactional
    public ResponseEntity updateUser(@RequestBody @Valid UpdateUserDTO dados) {
        var user = repository.getReferenceById(dados.id());
        user.updateInformations(dados);
        return ResponseEntity.ok(new DetailUserDTO(user));
    }

    @DeleteMapping("/{id}") // necessario chaves({}), pois, sem isso o Spring vai considerar que a URL para chamar esse método deve ser /medicos/id, ou seja, ele vai considerar que a palavra id faz parte da URL, e não que se trata de um parâmetro dinâmico.
    @Transactional
    public ResponseEntity deactivateUser(@PathVariable Long id){
        var user = repository.getReferenceById(id); // Recupera o medico do banco de dados
        //Seta atributo pra inativo
        user.deactivate();
        //Transactional realizara o update
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailUser(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetailUserDTO(user));
    }
}


