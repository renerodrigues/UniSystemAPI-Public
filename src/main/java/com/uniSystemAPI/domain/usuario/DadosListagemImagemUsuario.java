package com.uniSystemAPI.domain.usuario;

import java.nio.charset.StandardCharsets;

public record DadosListagemImagemUsuario(
    String id,
    String imagemBase64) {

  public DadosListagemImagemUsuario(Usuario usuario) {

    this(usuario.getId(), new String(usuario.getPessoa().getFotoBase64(), StandardCharsets.UTF_8));
  }

}
