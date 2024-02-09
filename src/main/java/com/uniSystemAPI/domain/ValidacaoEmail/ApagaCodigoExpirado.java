package com.uniSystemAPI.domain.ValidacaoEmail;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ApagaCodigoExpirado {

    // @PersistenceContext
    // private EntityManager entityManager;

    @Autowired
    ValidaEmailRepository repository;

    // @Autowired
    // private ThreadPoolTaskScheduler taskScheduler;
    @Transactional
    public void apagaCodigo() {

        Instant agora = LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")); // Obtém o horário atual
        System.out.println(agora);
       
        var itensExpirados = repository.findByExpiracaoBefore(agora);

        for (ValidaEmail item : itensExpirados) {
            repository.deleteById(item.getId()); // Remove os itens expirados do banco de dados
            System.out.println("codigo expirado apagado: " + item.getCodigo());
        }

    }

}
