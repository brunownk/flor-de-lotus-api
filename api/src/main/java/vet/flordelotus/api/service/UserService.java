package vet.flordelotus.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.flordelotus.api.domain.dto.CreateUserDTO;
import vet.flordelotus.api.domain.dto.UpdateUserDTO;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(CreateUserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());
        user.setImage(dto.image());
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UpdateUserDTO dto) {
        User user = userRepository.findById(dto.id()).orElseThrow();
        if (dto.username() != null) {
            user.setUsername(dto.username());
        }
        if (dto.password() != null) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
        if (dto.role() != null) {
            user.setRole(dto.role());
        }
        if (dto.image() != null) {
            user.setImage(dto.image());
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
