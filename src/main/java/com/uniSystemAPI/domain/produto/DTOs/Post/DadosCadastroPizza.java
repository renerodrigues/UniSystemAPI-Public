package com.uniSystemAPI.domain.produto.DTOs.Post;

import java.util.List;

public record DadosCadastroPizza(
        DadosCadastroProduto produto,
         List<String> saboresId,
        String estabelecimentoId
) {

}
