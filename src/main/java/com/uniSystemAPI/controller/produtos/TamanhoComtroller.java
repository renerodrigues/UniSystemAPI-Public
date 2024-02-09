package com.uniSystemAPI.controller.produtos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.domain.produto.DTOs.Get.DadosListagemTamanhos;
import com.uniSystemAPI.domain.produto.repositories.TamanhosRepository;

@RestController
@RequestMapping("tamanhos")
public class TamanhoComtroller {

    @Autowired
    protected TamanhosRepository tamanhosRepository;

    @GetMapping("{idestab}")
    // @Cacheable(value = CacheNames.NOME_CACHE_TAMANHOS)
    public List<DadosListagemTamanhos> listar(@PathVariable String idestab) {
      
        var tam = tamanhosRepository.findAllByEstabelecimentoIdOrderByNomeTamanho(idestab).stream()
                .map(DadosListagemTamanhos::new)
                .toList();

                System.out.println(tam);
return tam;
    }
}
