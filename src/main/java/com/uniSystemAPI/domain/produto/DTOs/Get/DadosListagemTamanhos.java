package com.uniSystemAPI.domain.produto.DTOs.Get;

import com.uniSystemAPI.domain.produto.pizza.Tamanhos;

public record DadosListagemTamanhos(
                String id,
                String nomeTamanho,
                int quantidadeDePedacos,
                int quantidadeDeSabores,
                boolean permitirVenda,
                String estabelecimentoId

) {

        public DadosListagemTamanhos(Tamanhos tamanho) {
                this(tamanho.getId(), tamanho.getNomeTamanho(), tamanho.getQuantidadeDePedacos(),
                                tamanho.getQuantidadeDeSabores(), tamanho.isPermitirVenda(),
                                tamanho.getEstabelecimentoId());
        }

}
