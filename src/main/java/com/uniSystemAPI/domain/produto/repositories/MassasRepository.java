package com.uniSystemAPI.domain.produto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniSystemAPI.domain.produto.pizza.Massas;

public interface MassasRepository extends JpaRepository<Massas, String>  {
    
}
