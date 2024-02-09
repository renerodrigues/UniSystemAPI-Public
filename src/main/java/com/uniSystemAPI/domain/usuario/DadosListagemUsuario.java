package com.uniSystemAPI.domain.usuario;

import com.uniSystemAPI.domain.util.MontarURLdeImagem; 

public record DadosListagemUsuario(
    String id,
    String nome,
    String email,
    String telefone,
    String fotoUrl,
    String nomeImagem) {

  public DadosListagemUsuario(Usuario usuario) {

    this(usuario.getId(), usuario.getPessoa().getNome(), usuario.getLogin(), usuario.getPessoa().getTelefone(),
        MontarURLdeImagem.montaURLImagemUsuario(usuario.getPessoa().getNomeImagem()),usuario.getPessoa().getNomeImagem()
    );
  }

  
}
