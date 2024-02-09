package com.uniSystemAPI.domain.ValidacaoEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosValidaEmail(
        @NotBlank @Email String emailDestinatario,
        String codigo) {

}
