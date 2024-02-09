package com.uniSystemAPI.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoEndereco(
                @NotBlank String idUsuario ,
                @NotNull DadosEndereco endereco

// String nomeImagem

) {

}
