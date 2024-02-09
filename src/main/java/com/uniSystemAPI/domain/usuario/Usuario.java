package com.uniSystemAPI.domain.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.PrimeiroAcesso.DadosCadastroPrimeiroAcesso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // MARCA A CLASSE COMO ENTIDADE
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "usuarios")
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
// #endregion -> anotações do Loombok para geração automatica de getters,
// setters, construtores sem argumentos e com argumentos
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private Pessoa pessoa;
    String estabelecimentoId;
    private String login;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Usuario(DadosCadastroUsuario cadastroUsuario) {

        this.pessoa = new Pessoa(cadastroUsuario.pessoa(), cadastroUsuario.imagemBase64());
        this.login = cadastroUsuario.login().toLowerCase();
        this.senha = cadastroUsuario.senha();
        this.estabelecimentoId = cadastroUsuario.estabelecimentoId();
        this.role = cadastroUsuario.role();
    }

    public Usuario(DadosCadastroPrimeiroAcesso cadastroPrimeiroAcesso) {
        this.pessoa = new Pessoa(cadastroPrimeiroAcesso.userPessoa(), cadastroPrimeiroAcesso.userImagemBase64());
        this.login = cadastroPrimeiroAcesso.userEmail().toLowerCase();
        this.senha = cadastroPrimeiroAcesso.userSenha();
        this.estabelecimentoId = cadastroPrimeiroAcesso.userEstabelecimentoId();
        this.role = cadastroPrimeiroAcesso.userRole();
    }

    public void atualizarDadosPessoaisUsuario(DadosAtualizacaoDadosPessoaisUsuario dados) {
        if (dados != null)
            this.pessoa.atualizarDadosPessoais(dados);
        if (dados.email() != null)
            this.login = dados.email().toLowerCase();

    }
    public void atualizarEnderecoUsuario(DadosAtualizacaoEndereco dados) {
        if (dados != null)
            this.pessoa.atualizarEndereco(dados);
         

    }

    public void atualizarImagemUsuario(DadosAtualizacaoImagemUsuario dados) {
        if (dados.nomeImagem() != null) {
            this.pessoa.atualizarImagem(dados.nomeImagem(), dados.imagemBase64());
        }

    }

    public void alterarSenha(String senha) {
        if (senha != null)
            this.senha = senha;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMINISTRADOR_DE_LOJA)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR_DE_LOJA"),
                    new SimpleGrantedAuthority("ROLE_VENDEDOR"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}