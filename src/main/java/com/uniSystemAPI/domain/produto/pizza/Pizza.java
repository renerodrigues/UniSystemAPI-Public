package com.uniSystemAPI.domain.produto.pizza;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.Produto;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroPizza;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pizza")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    // private String nomePizza;
    private String estabelecimentoId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private Produto produto;

    

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private List<Sabores> sabores;

    public Pizza(@Valid DadosCadastroPizza cadastroPizza) {

        this.estabelecimentoId = cadastroPizza.estabelecimentoId();
        this.produto = new Produto(cadastroPizza.produto());
        this.produto.setEstabelecimentoId(cadastroPizza.estabelecimentoId());

        // this.tamanhos = new ArrayList<>();
        // cadastroPizza.tamanhos().forEach((t) -> {
        // // Tamanhos tam = new Tamanhos(t, this);
        // // tam.setEstabelecimentoId(cadastroPizza.estabelecimentoId());
        // // this.tamanhos.add(tam);
        // });

        // this.massas = new ArrayList<>();
        // cadastroPizza.massas().forEach((m) -> {
        // Massas mass = new Massas(m, this);
        // mass.setEstabelecimentoId(cadastroPizza.estabelecimentoId());
        // this.massas.add(mass);
        // });

    }

}
