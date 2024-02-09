package com.uniSystemAPI.domain.estabelecimento;

import com.uniSystemAPI.domain.usuario.DadosEndereco;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroEstabelecimento(
                @NotNull String nomeFantasia,
                @NotNull String cpfCnpj,
                @NotNull String idUsuarioAdmin,
                @NotNull DadosEndereco endereco,
                @NotNull String logoMarcaBase64,
                @NotNull String nomeLogoMarca) {
}
