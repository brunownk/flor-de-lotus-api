package vet.flordelotus.api.domain.dto;

import vet.flordelotus.api.domain.entity.User;

public record DetailUserDTO(
        Long id,
        String login,
        String name,
        String username
) {
    public DetailUserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getName(), user.getUsername());
    }

}

