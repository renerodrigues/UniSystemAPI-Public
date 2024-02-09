package com.uniSystemAPI.domain.ValidacaoEmail;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "validaEmail")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
// #endregion -> anotações do Loombok para geração automatica de getters,
// setters, construtores sem argumentos e com argumentos
public class ValidaEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull
    private String email;
    private String codigo;
    private Instant expiracao;

    public ValidaEmail(@Valid DadosValidaEmail dados) {
        this.email = dados.emailDestinatario();
        this.codigo = dados.codigo();
        this.expiracao = getExpirationDate();
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusMinutes(3).toInstant(ZoneOffset.of("-03:00"));
    }
}
