package com.uniSystemAPI.domain.produto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroCategoria;
import com.uniSystemAPI.domain.produto.DTOs.Put.DadosAtualizacaoCategoria;
import com.uniSystemAPI.domain.produto.pizza.Bordas;
import com.uniSystemAPI.domain.produto.pizza.Massas;
import com.uniSystemAPI.domain.produto.pizza.Tamanhos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoria")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Categoria {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String nomeCategoria;
  private int index;
  private String estabelecimentoId;
  @Column(nullable = true) // O boolean default false define que a coluna terá um valor padrão de false se
                           // não for informado um valor.
  private boolean isPizza;
  @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private List<Tamanhos> tamanhos;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private List<Massas> massas;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private List<Bordas> bordas;

  public Categoria(DadosCadastroCategoria cadastroCategoria) {

    this.nomeCategoria = cadastroCategoria.nomeCategoria();
    this.estabelecimentoId = cadastroCategoria.estabelecimentoId();
    this.index = cadastroCategoria.index();
    this.isPizza = cadastroCategoria.isPizza();
  }

  public void atualizarCategoria(DadosAtualizacaoCategoria dados) {
    this.estabelecimentoId = dados.estabelecimentoId();
    this.index = dados.index();
    this.nomeCategoria = dados.nomeCategoria();

  }
  // Vegetariano
  // Vegano
  // Orgânico
  // Sem açúcar
  // Zero lactose

  // A categoria deve ser publica para todos os estabelecimentos
}
