package com.uniSystemAPI.domain.produto.DTOs.Post;

import com.uniSystemAPI.domain.produto.pizza.Pizza;

public record DadosCadastroMassas(
                 String id,
                String nomeMassa,
                double preco,
                boolean permitirVenda,
                String estabelecimentoId,
                Pizza pizza) {

}
