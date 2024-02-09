package com.uniSystemAPI.domain.produto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniSystemAPI.domain.produto.pizza.Bordas;

public interface BordasRepository extends JpaRepository<Bordas, String> {

    // Bordas  getReferenceByNomeBorda(String nomeBorda);

    Bordas findByNomeBorda(String nomeBorda);
   

}
