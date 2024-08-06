package vet.flordelotus.api.domain.dto;

import vet.flordelotus.api.domain.entity.User;

public record ListUserDTO(
        Long id,
        String login
) {
    public ListUserDTO(User user) {
        this(user.getId(), user.getLogin());
    }
}

