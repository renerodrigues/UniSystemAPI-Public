package com.uniSystemAPI.domain.produto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroProduto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;
    private String descricao;
    private double preco;
    private Boolean permitirVenda;
    private String nomeImagemProduto;
    private byte[] imagemBase64;
    private String estabelecimentoId;
    private Boolean sempreDisponivel; 
    private Integer indexHeader;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    // @JoinColumn(name = "produto_id") // ajuste o nome da coluna conforme
    // necessário
    private List<Disponibilidade> disponibilidade;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore // quando uma classe vai em branco pode ocorrer o erro No serializer found for
    private Categoria categoria;

    public Produto(DadosCadastroProduto produto) {
        this.nome = produto.nome();
        this.descricao = produto.descricao();
        this.preco = produto.preco();
        this.permitirVenda = produto.permitirVenda();
        this.nomeImagemProduto = produto.nomeImagemProduto();
        this.estabelecimentoId = produto.estabelecimentoId();
        // this.categoriaId = produto.categoriaId();
        this.sempreDisponivel = produto.sempreDisponivel();
        this.imagemBase64 = produto.imagemBase64().getBytes(StandardCharsets.UTF_8);
        this.disponibilidade = new ArrayList<>();
        this.indexHeader = produto.indexHeader();
        if (produto.sempreDisponivel() == false)
            produto.disponibilidade().forEach((e) -> {

                Disponibilidade disp = new Disponibilidade(e, this);
                disp.setEstabelecimentoId(produto.estabelecimentoId());
                this.disponibilidade.add(disp);
            });

    }

}
