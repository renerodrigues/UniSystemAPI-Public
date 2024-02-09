package com.uniSystemAPI.domain.produto.DTOs.Post;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroProduto(

        String nome,
        String descricao,
        @NotBlank double preco,
        @NotBlank boolean permitirVenda,
        String nomeImagemProduto,
        String imagemBase64,
        @NotBlank String estabelecimentoId,
        @NotBlank String categoriaId,
        @NotNull boolean sempreDisponivel,
        @NotNull int indexHeader,
        List<DadosCadastroDisponibilidade> disponibilidade

) {

}
