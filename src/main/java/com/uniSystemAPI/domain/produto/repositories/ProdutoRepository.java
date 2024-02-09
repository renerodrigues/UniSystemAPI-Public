package com.uniSystemAPI.domain.produto.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uniSystemAPI.domain.produto.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

        List<Produto> findAllByEstabelecimentoId(String estabelecimentoId, PageRequest pageRequest);

        // List<Pizza> findAllByEstabelecimentoIdOrderByCategoriaId(String idestab,
        // PageRequest pageRequest);
        // @Query("SELECT p FROM Pizza p " +
        //                 "INNER JOIN p.estabelecimento.id = :idestab " +
        //                 "ORDER BY p.categoria.id")
        // List<Produto> findAllByEstabelecimentoIdOrderByProdutoCategoriaId(@Param("idestab") String idestab,
        //                 PageRequest pageRequest);

        List<Produto> findAllByEstabelecimentoIdOrderByCategoriaIndex(String idestab, PageRequest pageRequest);

        Produto findByNomeImagemProduto(String nomeImagem);
}
