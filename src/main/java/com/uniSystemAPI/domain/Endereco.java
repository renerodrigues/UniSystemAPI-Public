package com.uniSystemAPI.domain;

import com.uniSystemAPI.domain.usuario.DadosAtualizacaoEndereco;
import com.uniSystemAPI.domain.usuario.DadosEndereco;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
// #endregion -> anotações do Loombok para geração automatica de getters,
// setters, construtores sem argumentos e com argumentos
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String complemento;
    private String cidade;
    private String uf;
    private String instrucoesParaEntrega;

    public Endereco(DadosEndereco endereco) {
        this.cep = endereco.cep();
        this.rua = endereco.rua();
        this.numero = endereco.numero();
        this.bairro = endereco.bairro();
        this.complemento = endereco.complemento();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
        this.instrucoesParaEntrega = endereco.instrucoesParaEntrega();
    }

    public void atualizarEndereco(DadosAtualizacaoEndereco endereco) {

        if (endereco.endereco().rua() != null)
            this.rua = endereco.endereco().rua();

        if (endereco.endereco().bairro() != null)
            this.bairro = endereco.endereco().bairro();

        if (endereco.endereco().cep() != null)
            this.cep = endereco.endereco().cep();

        if (endereco.endereco().numero() != null)
            this.numero = endereco.endereco().numero();

        if (endereco.endereco().complemento() != null)
            this.complemento = endereco.endereco().complemento();

        if (endereco.endereco().cidade() != null)
            this.cidade = endereco.endereco().cidade();

        if (endereco.endereco().uf() != null)
            this.uf = endereco.endereco().uf();
            
        if (endereco.endereco().instrucoesParaEntrega() != null)
            this.instrucoesParaEntrega = endereco.endereco().instrucoesParaEntrega();
    }
}