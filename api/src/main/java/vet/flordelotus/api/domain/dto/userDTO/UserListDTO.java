package vet.flordelotus.api.domain.dto.userDTO;

import vet.flordelotus.api.domain.entity.User;

public record UserListDTO(
        Long id,
        String login
) {
    public UserListDTO(User user) {
        this(user.getId(), user.getLogin());
    }
}

