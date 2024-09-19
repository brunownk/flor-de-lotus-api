package vet.flordelotus.api.domain.dto.userDTO;

import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.enums.role.Role;

public record UserDetailDTO(
        Long id,
        String username,
        String name,
        String email,
        Boolean active,
        Role role

) {
    public UserDetailDTO(User user) {
        this(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getActive(), user.getRole());
    }

}

