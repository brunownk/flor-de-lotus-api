package vet.flordelotus.api.infra.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import vet.flordelotus.api.enums.role.Role;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.domain.repository.UserRepository;

@Component
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin") == null) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setName("admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setRole(Role.ADMIN);

            String rawPassword = "password";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            adminUser.setPassword(encodedPassword);

            userRepository.save(adminUser);
        }

        System.out.println("Admin user seeded");
    }
}