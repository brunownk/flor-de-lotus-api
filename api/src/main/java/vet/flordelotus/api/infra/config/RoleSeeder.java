package vet.flordelotus.api.infra.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import vet.flordelotus.api.domain.entity.Role;
import vet.flordelotus.api.domain.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

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

        System.out.println("Roles Seeded");
    }
}

