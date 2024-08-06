package vet.flordelotus.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.flordelotus.api.domain.dto.CreateUserDTO;
import vet.flordelotus.api.domain.dto.UpdateUserDTO;
import vet.flordelotus.api.domain.repository.UserRepository;
import vet.flordelotus.api.domain.entity.User;

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
        user.setLogin(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UpdateUserDTO dto) {
        User user = userRepository.findById(dto.id()).orElseThrow();
        if (dto.username() != null) {
            user.setLogin(dto.username());
        }
        if (dto.password() != null) {
            user.setPassword(passwordEncoder.encode(dto.password()));
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

}

