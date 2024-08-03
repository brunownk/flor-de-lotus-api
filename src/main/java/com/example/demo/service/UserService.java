package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // This tells Spring that this class is a service
public class UserService {

    @Autowired  // This tells Spring to inject the UserRepository dependency
    private UserRepository userRepository;

    @Autowired  // This tells Spring to inject the PasswordEncoder dependency
    private PasswordEncoder passwordEncoder;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create a new user
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt the password
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(userDetails.getEmail());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));  // Encrypt the new password
        user.setPhoto(userDetails.getPhoto());
        user.setStatus(userDetails.getStatus());
        return userRepository.save(user);
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
