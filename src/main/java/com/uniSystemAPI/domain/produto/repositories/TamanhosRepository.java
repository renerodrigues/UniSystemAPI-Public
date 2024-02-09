package com.uniSystemAPI.domain.produto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniSystemAPI.domain.produto.pizza.Tamanhos;

public interface TamanhosRepository extends JpaRepository<Tamanhos, String>  {

      List<Tamanhos> findAllByEstabelecimentoIdOrderByNomeTamanho(String idestab);
 
}
