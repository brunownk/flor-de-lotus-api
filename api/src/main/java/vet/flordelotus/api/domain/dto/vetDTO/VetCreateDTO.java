package vet.flordelotus.api.domain.dto.vetDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import vet.flordelotus.api.enums.vet.Specialty;


public record VetCreateDTO(


        @NotBlank(message = "{nome.obrigatorio}") //Verifica se nao eh nulo nem vazio (somente para Strings)
        String name,
        @NotBlank(message = "{nome.obrigatorio}") //Verifica se nao eh nulo nem vazio (somente para Strings)
        String username,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}") //Expressao regular para o padrao do CRM \\d = digitos 4,6 = de 4 a 6 digitos
        String crmv,
        @NotNull
        Specialty specialty
        ) {
}
