package com.uniSystemAPI.domain.produto.DTOs.Post;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroCategoria(
        @NotNull String nomeCategoria,
        @NotNull String estabelecimentoId,
        @NotNull int index,
        @NotNull boolean isPizza,
        List<DadosCadastroTamanhos> tamanhos,
        List<DadosCadastroMassas> massas,
        List<DadosCadastroBordas> bordas) {

}
