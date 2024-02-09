package com.uniSystemAPI.domain.estabelecimento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, String> {
    
      Estabelecimento findByCpfCnpj(String cpfCnpj);
}
