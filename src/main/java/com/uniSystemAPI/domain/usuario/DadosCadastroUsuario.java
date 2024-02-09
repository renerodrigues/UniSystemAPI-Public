package com.uniSystemAPI.domain.usuario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUsuario(
        @NotNull @Valid /* @Valid valida as validações do objeto */
        DadosCadastroPessoa pessoa,

        @NotBlank /* NotBlank é somente para String, caso contrario utilizar NotNull */
        @Email String login,

      //  @NotBlank String cpfCnpj,

        /* @Valid valida as validações do objeto */
        @NotBlank String estabelecimentoId,

        @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$") String senha,

        @NotNull UserRole role,

        @NotBlank String imagemBase64

      

) {

}
