package com.uniSystemAPI.domain.produto.pizza;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroSabores;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sabores")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sabores {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nomeSabor;
    private String descricao;
    private double preco;
    private String nomeImagem;
    private boolean permitirVenda;
    private String estabelecimentoId;

    // @ManyToMany(cascade = CascadeType.PERSIST)
    // @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No
    // serializer found for
    // private List<Pizza> pizzas;

    // @ManyToMany(cascade = CascadeType.PERSIST)
    // @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    // private List<Classificacao> classificacao;

    public Sabores(@Valid DadosCadastroSabores cadastroSabores) {
        this.nomeSabor = cadastroSabores.nomeSabor();
        this.descricao = cadastroSabores.descricao();
        this.preco = cadastroSabores.preco();
        this.nomeImagem = cadastroSabores.nomeImagem();
        this.permitirVenda = cadastroSabores.permitirVenda();
        this.estabelecimentoId = cadastroSabores.estabelecimentoId();
    }

}
