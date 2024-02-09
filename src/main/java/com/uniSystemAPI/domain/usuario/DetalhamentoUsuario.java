package com.uniSystemAPI.domain.usuario;

import com.uniSystemAPI.domain.Endereco;
import com.uniSystemAPI.domain.estabelecimento.DetalhamentoEstabelecimento;
import com.uniSystemAPI.domain.util.MontarURLdeImagem;

public record DetalhamentoUsuario(
        String id,
        String nome,
        String dataNascimento,
        String cpf,
        String telefone,
        String email,
        String estabelecimentoId,
        DetalhamentoEstabelecimento Estabelecimento,
        UserRole cargo,
        String fotoURL,
        Endereco endereco) {

    public DetalhamentoUsuario(Usuario usuario, DetalhamentoEstabelecimento estabelecimento) {
        this(
                usuario.getId(),
                usuario.getPessoa().getNome(),
                usuario.getPessoa().getDataNascimento(),
                usuario.getPessoa().getCpf(),
                usuario.getPessoa().getTelefone(),
                usuario.getLogin(),
                usuario.getEstabelecimentoId(),
                estabelecimento,
                usuario.getRole(),
                MontarURLdeImagem.montaURLImagemUsuario(usuario.getPessoa().getNomeImagem()),
                usuario.getPessoa().getEndereco());
    }

}
