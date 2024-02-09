package com.uniSystemAPI.domain.produto.DTOs.Post;

public record DadosCadastroTamanhos(
                String id,
                String nomeTamanho,
                int quantidadeDePedacos,
                int quantidadeDeSabores,
                boolean permitirVenda,
                String estabelecimentoId // ,
// Pizza pizza
) {

}
