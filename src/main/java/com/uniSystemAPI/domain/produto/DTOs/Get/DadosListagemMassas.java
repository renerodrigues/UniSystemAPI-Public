package com.uniSystemAPI.domain.produto.DTOs.Get;

import com.uniSystemAPI.domain.produto.pizza.Massas;

public record DadosListagemMassas(
                String id,
                String nomeMassa,
                double preco,
                boolean permitirVenda,
                String estabelecimentoId

) {

        public DadosListagemMassas(Massas massa) {
                this(massa.getId(), massa.getNomeMassa(), massa.getPreco(), massa.isPermitirVenda(),
                                massa.getEstabelecimentoId());
        }

}
