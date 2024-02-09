package com.uniSystemAPI.domain.produto.DTOs.Post;

public record DadosCadastroSabores(
                String nomeSabor,
                String descricao,
                double preco,
                String nomeImagem,
                String imagemBase64,
                boolean permitirVenda,
                String estabelecimentoId) {

}
