package vet.flordelotus.api.domain.dto.vetDTO;

import jakarta.validation.constraints.NotNull;

//Utilia o DTO DadosEndereco, mas se tivesse algum dado que nao pudesse alterar, teria que criar outro DTO
public record VetUpdateDTO(
        @NotNull
        Long id,
        String name,
        String username) {
}
