package com.uniSystemAPI.domain.usuario;
 
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoDadosPessoaisUsuario(
                @NotBlank String id,
                @NotBlank String nome,
                @NotBlank String dataNascimento,
                @NotBlank String cpf,
                @NotBlank String telefone,
                String email
// @NotBlank String email
) {

}
