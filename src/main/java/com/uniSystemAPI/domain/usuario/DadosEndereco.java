package com.uniSystemAPI.domain.usuario;

import jakarta.validation.constraints.NotBlank;
 

public record DadosEndereco(
        @NotBlank  
        String cep,

        String rua,

        @NotBlank  
        String numero,
        String bairro,
        String complemento,
        String cidade,
        String uf,
        String instrucoesParaEntrega) {

}
