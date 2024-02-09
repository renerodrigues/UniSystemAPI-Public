package com.uniSystemAPI.domain.produto.itemPricipal;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.estabelecimento.Estabelecimento;
import com.uniSystemAPI.domain.produto.Classificacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "complementospreparados")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class ComplementosPreparados {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nomeComplemento;
    private String descicao;
    private boolean permitirVenda;
    private double preco;
    private double nomeImagem;

     @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private Estabelecimento estabelecimento;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private List<GrupoDeComplementos> grupoDeComplementos;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private List<Classificacao> classificacao;
}
