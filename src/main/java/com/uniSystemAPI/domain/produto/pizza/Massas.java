package com.uniSystemAPI.domain.produto.pizza;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroMassas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "massas")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Massas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nomeMassa;
    private double preco;
    private boolean permitirVenda;
    private String estabelecimentoId;

    // @ManyToOne(cascade = CascadeType.PERSIST)
    // @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No
    // private Pizza pizzas;

    public Massas(DadosCadastroMassas massa) {
        System.out.println("massas " + massa);
        this.nomeMassa = massa.nomeMassa();
        this.preco = massa.preco();
        this.permitirVenda = massa.permitirVenda();
        this.estabelecimentoId = massa.estabelecimentoId();
        // this.pizzas = pizza;
    }
}
