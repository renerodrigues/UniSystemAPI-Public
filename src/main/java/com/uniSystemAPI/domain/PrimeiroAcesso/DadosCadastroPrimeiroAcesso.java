package com.uniSystemAPI.domain.PrimeiroAcesso;

import com.uniSystemAPI.domain.usuario.DadosEndereco;
import com.uniSystemAPI.domain.usuario.DadosCadastroPessoa;
import com.uniSystemAPI.domain.usuario.UserRole;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroPrimeiroAcesso(
        @NotBlank String estabNomeFantasia,
        @NotBlank String estabCpfCnpj,
         String estabIdUsuarioAdmin,
        @NotNull DadosEndereco estabEndereco,
        @NotBlank String estabLogoMarcaBase64,
        @NotBlank String estabNomeLogoMarca,

        @NotNull @Valid DadosCadastroPessoa userPessoa,
        @NotBlank /* NotBlank é somente para String, caso contrario utilizar NotNull */
     @NotBlank    @Email String userEmail,
        /* @Valid valida as validações do objeto */
         String userEstabelecimentoId,
        @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$") String userSenha,
        @NotNull UserRole userRole,
        @NotBlank String userImagemBase64,
        @NotBlank String userNomeImagem

) {

}
