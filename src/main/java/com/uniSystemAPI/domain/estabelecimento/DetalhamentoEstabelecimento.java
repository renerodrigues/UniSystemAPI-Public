package com.uniSystemAPI.domain.estabelecimento;

import com.uniSystemAPI.domain.Endereco;
import com.uniSystemAPI.domain.util.MontarURLdeImagem;

public record DetalhamentoEstabelecimento(
        String id,
        String nomeFantasia,
        String cpfCnpj,
        String logoURL,
        Endereco endereco) {

    public DetalhamentoEstabelecimento(Estabelecimento estabelecimento) {

        this(
                estabelecimento.getId(),
                estabelecimento.getNomeFantasia(),
                estabelecimento.getCpfCnpj(),
                MontarURLdeImagem.montaURLImagemEstabelecimento(estabelecimento.getLogoMarca()),
                estabelecimento.getEndereco());
    }
}
