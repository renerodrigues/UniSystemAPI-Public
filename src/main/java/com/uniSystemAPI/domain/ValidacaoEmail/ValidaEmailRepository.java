package com.uniSystemAPI.domain.ValidacaoEmail;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidaEmailRepository extends JpaRepository<ValidaEmail, String> {
    // @Query("""
    //         SELECT i FROM ValidaEmail i WHERE i.email = :email
    //             """)
     List<ValidaEmail> findByEmail(String email);

    // @Query("""
    //         SELECT i FROM ValidaEmail i WHERE i.expiracao <= :agora
    //             """)
    // List<ValidaEmail> findByExpiracao(Instant agora);

    List<ValidaEmail> findByExpiracaoBefore(Instant agora);

    void deleteByEmail(String email);

}
