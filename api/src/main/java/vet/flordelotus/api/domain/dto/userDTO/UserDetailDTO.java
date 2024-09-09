package vet.flordelotus.api.domain.dto.userDTO;

import vet.flordelotus.api.domain.entity.User;

public record UserDetailDTO(
        Long id,
        String username,
        String name,
        String email,
        Boolean active

) {
    public UserDetailDTO(User user) {
        this(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getActive());
    }

}

