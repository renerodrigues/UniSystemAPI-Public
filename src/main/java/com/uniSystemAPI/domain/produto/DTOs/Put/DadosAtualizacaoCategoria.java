package com.uniSystemAPI.domain.produto.DTOs.Put;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public record DadosAtualizacaoCategoria(
        @NotNull String id,
        @NotNull String nomeCategoria,
        @NotNull int index,
        @NotNull String estabelecimentoId) {

}
