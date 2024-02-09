package com.uniSystemAPI.domain.usuario;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.Endereco;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "pessoas")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
// #endregion -> anotações do Loombok para geração automatica de getters,
// setters, construtores sem argumentos e com argumentos

public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String dataNascimento;
    private String cpf;
    private String telefone;
    private String email;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    // @Embedded no banco de dados os campos da classe endereço ficarão na mesma
    // tabela da classe pessoa
    private Endereco endereco;

    private String nomeImagem;

    private byte[] fotoBase64;

    public Pessoa(DadosCadastroPessoa pessoa, String imagemBase64) {
        this.nome = pessoa.nome();
        this.dataNascimento = pessoa.dataNascimento();
        this.cpf = pessoa.cpf();
        this.telefone = pessoa.telefone();
        this.endereco = new Endereco(pessoa.endereco());
        this.nomeImagem = pessoa.nomeImagem();
        this.fotoBase64 = imagemBase64.getBytes(StandardCharsets.UTF_8);

    }

    public void atualizarDadosPessoais(DadosAtualizacaoDadosPessoaisUsuario pessoa) {

        if (pessoa.nome() != null)
            this.nome = pessoa.nome();

        if (pessoa.dataNascimento() != null)
            this.dataNascimento = pessoa.dataNascimento();

        if (pessoa.cpf() != null)
            this.cpf = pessoa.cpf();

        if (pessoa.telefone() != null)
            this.telefone = pessoa.telefone();

        if (pessoa.email() != null)
            this.email = pessoa.email();
    }

    public void atualizarImagem(String foto, String imagemBase64) {
        if (foto != null) {
            this.nomeImagem = foto;
            this.fotoBase64 = imagemBase64.getBytes(StandardCharsets.UTF_8);
        }
    }

    public void atualizarEndereco(DadosAtualizacaoEndereco dados) {
     this.endereco.atualizarEndereco(dados);
    }

}