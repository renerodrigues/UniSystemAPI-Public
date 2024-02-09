package com.uniSystemAPI.domain.produto.pizza;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroTamanhos;

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
@Table(name = "tamanhos")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tamanhos {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String nomeTamanho;

  private int quantidadeDePedacos;
  private int quantidadeDeSabores;
  private boolean permitirVenda;
  private String estabelecimentoId;

  public Tamanhos(DadosCadastroTamanhos tamanhos) {
    System.out.println("tamanhos " + tamanhos);
    this.nomeTamanho = tamanhos.nomeTamanho();
    this.quantidadeDePedacos = tamanhos.quantidadeDePedacos();
    this.quantidadeDeSabores = tamanhos.quantidadeDeSabores();
    this.permitirVenda = tamanhos.permitirVenda();
    this.estabelecimentoId = tamanhos.estabelecimentoId();
    // this.pizza = pizza;
  }

}
