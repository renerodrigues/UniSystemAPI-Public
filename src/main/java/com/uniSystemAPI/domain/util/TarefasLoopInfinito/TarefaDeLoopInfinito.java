package com.uniSystemAPI.domain.util.TarefasLoopInfinito;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.uniSystemAPI.domain.ValidacaoEmail.ApagaCodigoExpirado;

@Component
public class TarefaDeLoopInfinito {
    
    @Autowired
    ApagaCodigoExpirado apagCodigoRepository;

    @Scheduled(fixedDelay = 10000) // Executa a cada x segundos
    public void apagaCodigosExpirados() {
        apagCodigoRepository.apagaCodigo();
     
    }

}
