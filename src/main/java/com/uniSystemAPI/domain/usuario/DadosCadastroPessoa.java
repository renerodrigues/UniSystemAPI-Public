package com.uniSystemAPI.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPessoa(
                @NotBlank String nome,
                @NotBlank String dataNascimento,
                @NotBlank String cpf,
                @NotBlank String telefone,
                @NotBlank String email,
                String nomeImagem,
                @NotNull DadosEndereco endereco

//

) {

}
