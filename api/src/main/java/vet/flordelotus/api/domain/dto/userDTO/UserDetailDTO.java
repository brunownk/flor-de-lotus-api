package vet.flordelotus.api.domain.dto.userDTO;

import vet.flordelotus.api.domain.entity.User;

public record UserDetailDTO(
        Long id,
        String login

) {
    public UserDetailDTO(User user) {
        this(user.getId(), user.getLogin());
    }

}

