package com.uniSystemAPI.domain.produto.itemPricipal;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.estabelecimento.Estabelecimento;

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
@Table(name = "itempreparado")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class ItemPreparado {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nomeItemPrep;
    private String descricaoItemPrep;
    private String nomeImagemItemPrep;
    private boolean ativarEstoque;
    private int estoque;
    private double peso;
    private boolean aplicarDesconto;
    private double desconto;
    private double valorComDesconto;

     @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private Estabelecimento estabelecimento;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private List<GrupoDeComplementos> grupoDeComplementos;
}
