package com.uniSystemAPI.domain.produto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniSystemAPI.domain.produto.pizza.Sabores;

public interface SaboresRepository extends JpaRepository<Sabores, String>  {
    
}
