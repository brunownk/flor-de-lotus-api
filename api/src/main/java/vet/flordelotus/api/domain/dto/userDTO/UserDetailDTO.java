package vet.flordelotus.api.domain.dto.userDTO;

import vet.flordelotus.api.domain.entity.User;

public record UserDetailDTO(
        Long id,
        String login,
        String name,
        String username,
        String crmv
) {
    public UserDetailDTO(User user) {
        this(user.getId(), user.getLogin(), user.getName(), user.getUsername(), user.getCrmv());
    }

}

