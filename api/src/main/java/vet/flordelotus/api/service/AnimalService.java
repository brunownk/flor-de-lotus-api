package vet.flordelotus.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.flordelotus.api.domain.dto.CreateAnimalDTO;
import vet.flordelotus.api.domain.dto.UpdateAnimalDTO;
import vet.flordelotus.api.domain.entity.Animal;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.AnimalRepository;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Animal createAnimal(CreateAnimalDTO dto) {
        User owner = userRepository.findById(dto.ownerId()).orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        Animal animal = new Animal(dto, owner);
        return animalRepository.save(animal);
    }

    @Transactional
    public Animal updateAnimal(UpdateAnimalDTO dto) {
        Animal animal = animalRepository.findById(dto.id()).orElseThrow();
        animal.updateInformations(dto);
        return animalRepository.save(animal);
    }

    @Transactional
    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Animal not found"));
        animal.delete();
        animalRepository.save(animal);
    }

    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id).filter(Animal::getActive);
    }

    public List<Animal> getAnimalsByOwner(Long ownerId) {
        return animalRepository.findByOwnerIdAndActiveTrue(ownerId);
    }

    public Page<Animal> getAllAnimals(Pageable pageable) {
        return animalRepository.findByActiveTrue(pageable);
    }
}
