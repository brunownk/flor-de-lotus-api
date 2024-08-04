package vet.flordelotus.api.domain.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record DadosAtualizacaoAnimal(
        @NotNull(message = "{id.required}")
        Long id,

        @NotBlank(message = "{name.required}")
        @Size(max = 100, message = "{name.size}")
        String name,

        @NotNull(message = "{breedId.required}")
        Long breedId,

        @NotNull(message = "{ownerId.required}")
        Long ownerId,

        LocalDateTime deletedAt,
        Long deletedById
) {}
