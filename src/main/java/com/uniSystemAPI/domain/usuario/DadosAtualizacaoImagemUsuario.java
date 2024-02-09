package com.uniSystemAPI.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoImagemUsuario(
                @NotNull String id,
                String nomeImagem,
                String imagemBase64) {

}
