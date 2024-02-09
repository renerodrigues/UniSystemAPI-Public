package com.uniSystemAPI.domain.produto.itemPricipal;

import com.uniSystemAPI.domain.estabelecimento.Estabelecimento;
import com.uniSystemAPI.domain.produto.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "itemprincipal")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemPrincipal {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private String id;
   private String nomeItem;
   private String descricaoItem;
   
   @ManyToOne(cascade = CascadeType.PERSIST)
   @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
   private Produto produto;

   @ManyToOne(cascade = CascadeType.PERSIST)
   @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
   private Estabelecimento estabelecimento;

   @ManyToOne(cascade = CascadeType.PERSIST)
   @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
   private ItemPreparado itemPreparado;

}
