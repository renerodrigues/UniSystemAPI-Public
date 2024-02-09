package com.uniSystemAPI.domain.estabelecimento;

import com.uniSystemAPI.domain.Endereco;
import com.uniSystemAPI.domain.util.MontarURLdeImagem;

public record DadosListagemEstabelecimento(
    String id,
    String nomeFantasia,
    String cpfCnpj,
    String logoMarca,
    Endereco endereco) {

  public DadosListagemEstabelecimento(Estabelecimento estabelecimento) {

    this(estabelecimento.getId(), estabelecimento.getNomeFantasia(), estabelecimento.getCpfCnpj(),
        MontarURLdeImagem.montaURLImagemEstabelecimento(estabelecimento.getLogoMarca()), estabelecimento.getEndereco());
  }

}
