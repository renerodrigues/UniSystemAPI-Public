package com.uniSystemAPI.domain.produto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.uniSystemAPI.domain.produto.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, String> {

      List<Categoria> findAllByEstabelecimentoIdOrderByIndex(String idestab);

      Categoria findByNomeCategoriaAndEstabelecimentoIdAndTamanhosNomeTamanhoAndTamanhosEstabelecimentoId(
                  String nomeCategoria,
                  String estabelecimentoId,
                  String nomeBorda,
                  String tamanhosEstabelecimentoId);

      Categoria findByNomeCategoriaAndEstabelecimentoIdAndMassasNomeMassaAndMassasEstabelecimentoId(
                  String nomeCategoria,
                  String estabelecimentoId,
                  String nomeMassa,
                  String massasEstabelecimentoId);

      Categoria findByNomeCategoriaAndEstabelecimentoIdAndBordasNomeBordaAndBordasEstabelecimentoId(
                  String nomeCategoria,
                  String estabelecimentoId,
                  String nomeBorda,
                  String bordasstabelecimentoId);

      Categoria findByNomeCategoriaAndEstabelecimentoId(
                  String nomeCategoria,
                  String estabelecimentoId);

      @Transactional
      void deleteByNomeCategoriaAndEstabelecimentoId(
                  String nomeCategoria,
                  String estabelecimentoId);

}
