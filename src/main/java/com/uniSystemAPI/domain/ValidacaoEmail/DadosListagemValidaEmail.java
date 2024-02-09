package com.uniSystemAPI.domain.ValidacaoEmail;

import java.time.Instant;

public record DadosListagemValidaEmail(String id, String email, String codigo, Instant expiracao) {

    public DadosListagemValidaEmail(ValidaEmail email) {
        this(email.getId(), email.getEmail(), email.getCodigo(),
                email.getExpiracao());
    }
}
