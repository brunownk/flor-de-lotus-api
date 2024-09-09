package vet.flordelotus.api.infra.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import vet.flordelotus.api.domain.entity.Role;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.RoleRepository;
import vet.flordelotus.api.domain.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        List<String> roles = Arrays.asList("ADMIN", "USER", "VETERINARIAN", "CUSTUMER");

        for (String roleName : roles) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
        if (userRepository.findByUsername("admin") == null) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setName("admin");
            adminUser.setEmail("admin@gmail.com");

            String rawPassword = "password";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            adminUser.setPassword(encodedPassword);

            Role adminRole = roleRepository.findByName("ADMIN");

            if (adminRole != null) {
                adminUser.setRoles(Collections.singleton(adminRole));
            } else {
                System.out.println("Role ADMIN not found. Admin user created without ADMIN role.");
            }

            userRepository.save(adminUser);
        }

        System.out.println("Roles and admin user seeded");
    }
}
