package com.uniSystemAPI.domain.Login;

import com.uniSystemAPI.domain.usuario.Usuario;

public record TokenValidoDTO(String userId, String estabId) {

    public TokenValidoDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getEstabelecimentoId());
    }

}
