package com.uniSystemAPI.domain.produto.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uniSystemAPI.domain.produto.pizza.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, String> {

    List<Pizza> findAllByEstabelecimentoId(String estabelecimentoId, PageRequest pageRequest);

    // List<Pizza> findAllByEstabelecimentoIdOrderByCategoriaId(String idestab,
    // PageRequest pageRequest);
   
    
}
