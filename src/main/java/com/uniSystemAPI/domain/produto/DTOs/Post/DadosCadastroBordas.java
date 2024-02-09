package com.uniSystemAPI.domain.produto.DTOs.Post;

public record DadosCadastroBordas(
       String id,
        String nomeBorda,
        double preco,
        boolean permitirVenda,
        String estabelecimentoId) {

}
