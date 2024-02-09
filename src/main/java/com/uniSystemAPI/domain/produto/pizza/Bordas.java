package com.uniSystemAPI.domain.produto.pizza;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroBordas;

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
@Table(name = "bordas")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bordas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nomeBorda;
    private double preco;
    private boolean permitirVenda;
    private String estabelecimentoId;

    public Bordas(@Valid DadosCadastroBordas cadastroBordas) {
        System.out.println("cadastroBordas " + cadastroBordas);
   
        this.nomeBorda = cadastroBordas.nomeBorda();
        this.preco = cadastroBordas.preco();
        this.permitirVenda = cadastroBordas.permitirVenda();
        this.estabelecimentoId = cadastroBordas.estabelecimentoId();
    }
    // @ManyToMany(cascade = CascadeType.PERSIST)
    // @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No
    // serializer found for
    // private List<Pizza> pizzas;
}
