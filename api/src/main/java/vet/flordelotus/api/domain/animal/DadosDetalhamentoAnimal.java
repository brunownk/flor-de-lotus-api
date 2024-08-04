package vet.flordelotus.api.domain.animal;

import java.time.LocalDateTime;

public record DadosDetalhamentoAnimal(
        Long id,
        String name,
        Long breedId,
        Long ownerId,
        LocalDateTime createdAt,
        LocalDateTime deletedAt,
        Long createdById,
        Long deletedById
) {}

