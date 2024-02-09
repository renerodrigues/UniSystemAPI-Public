package com.uniSystemAPI.domain.produto.DTOs.Get;

import com.uniSystemAPI.domain.produto.pizza.Bordas;

public record DadosListagemBordas(
                String id,
                String nomeBorda,
                double preco,
                boolean permitirVenda,
                String estabelecimentoId

) {

        public DadosListagemBordas(Bordas borda) {
                this(borda.getId(), borda.getNomeBorda(), borda.getPreco(), borda.isPermitirVenda(),
                                borda.getEstabelecimentoId());
        }

}
