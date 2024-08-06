package vet.flordelotus.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vet.flordelotus.api.domain.animal.Gender;
import vet.flordelotus.api.domain.animal.Species;
import vet.flordelotus.api.domain.entity.User;

import java.time.LocalDate;

public record CreateAnimalDTO(

        @NotBlank(message = "O nome do animal é obrigatório.")
        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres.")
        String name,

        @NotNull(message = "O ID do proprietário é obrigatório.")
        Long userId,

        @NotNull(message = "A espécie é obrigatória.")
        Species species,

        @Size(max = 100, message = "A raça deve ter no máximo 100 caracteres.")
        String breed,

        @NotNull(message = "O gênero é obrigatório.")
        Gender gender,

        LocalDate dateOfBirth,

        @NotNull(message = "O ID do criador é obrigatório.")
        Long createdById
) {}
