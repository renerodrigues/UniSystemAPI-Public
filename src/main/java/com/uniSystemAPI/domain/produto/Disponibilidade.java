package com.uniSystemAPI.domain.produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroDisponibilidade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "disponibilidade")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String diadasemana;
    private String horario;

    private String estabelecimentoId;

    // @ManyToMany(cascade = CascadeType.PERSIST)
    // serializer found for
    // private List<Produto> produto; 
   // String produtoId;
    @ManyToOne(cascade = CascadeType.PERSIST)
     @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No
   private Produto produto;

    public Disponibilidade(DadosCadastroDisponibilidade disponibilidade, Produto produto) {

        this.diadasemana = disponibilidade.diadasemana();
        this.horario = disponibilidade.horario();
        this.estabelecimentoId = disponibilidade.estabelecimentoId();
        this.produto = produto;

    }
 

}
