package com.uniSystemAPI.domain.produto.DTOs.Post;

import com.uniSystemAPI.domain.produto.Produto;

public record DadosCadastroDisponibilidade(

        String diadasemana,
        String horario,
         String estabelecimentoId,
           Produto produto
        // List<DadosCadastroProduto> produto
        ) {

    public DadosCadastroDisponibilidade disponibilidade() {
        return null;
    }

}
