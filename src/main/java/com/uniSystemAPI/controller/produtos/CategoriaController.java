package com.uniSystemAPI.controller.produtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniSystemAPI.domain.estabelecimento.EstabelecimentoRepository;
import com.uniSystemAPI.domain.produto.Categoria;
import com.uniSystemAPI.domain.produto.DTOs.DELETE.DadosApagarCategoria;
import com.uniSystemAPI.domain.produto.DTOs.Get.DadosListagemCategorias;
import com.uniSystemAPI.domain.produto.DTOs.Post.DadosCadastroCategoria;
import com.uniSystemAPI.domain.produto.DTOs.Put.DadosAtualizacaoCategoria;
import com.uniSystemAPI.domain.produto.pizza.Bordas;
import com.uniSystemAPI.domain.produto.pizza.Massas;
import com.uniSystemAPI.domain.produto.pizza.Tamanhos;
import com.uniSystemAPI.domain.produto.repositories.BordasRepository;
import com.uniSystemAPI.domain.produto.repositories.CategoriaRepository;
import com.uniSystemAPI.domain.produto.repositories.MassasRepository;
import com.uniSystemAPI.domain.produto.repositories.TamanhosRepository;
import com.uniSystemAPI.domain.util.CacheNames;

import jakarta.validation.Valid;

@RestController
@RequestMapping("categorias")
public class CategoriaController {
   
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private BordasRepository bordasRepository;

    @Autowired
    private MassasRepository massasRepository;

    @Autowired
    private TamanhosRepository tamanhosRepository;

    @PostMapping("novaCategoria")
    // @CacheEvict(value = nomeCacheCategoria, allEntries = true)
    @CacheEvict(value = CacheNames.NOME_CACHE_CATEGORIA, allEntries = true)
     public ResponseEntity<String> cadastrarCategoria(@RequestBody @Valid DadosCadastroCategoria cadastroCategoria) {
        System.out.println("Categoria " + cadastroCategoria);

        try {
            if (this.estabelecimentoRepository.findById(cadastroCategoria.estabelecimentoId()) == null
                    || cadastroCategoria.estabelecimentoId().equals(""))
                return ResponseEntity.badRequest().body("Erro! O estabelecimento não foi encontrado");

            var cat = categoriaRepository
                    .findByNomeCategoriaAndEstabelecimentoId(cadastroCategoria.nomeCategoria(),
                            cadastroCategoria.estabelecimentoId());
            if (cat != null)
                return ResponseEntity.badRequest()
                        .body("Erro ao Salvar! A categoria \"" + cadastroCategoria.nomeCategoria() + "\" já existe");

            List<Tamanhos> tamanho = new ArrayList<>();
            for (var t : cadastroCategoria.tamanhos()) {
                if (t.id().equals("")) {

                    var tm = categoriaRepository
                            .findByNomeCategoriaAndEstabelecimentoIdAndTamanhosNomeTamanhoAndTamanhosEstabelecimentoId(
                                    cadastroCategoria.nomeCategoria(), cadastroCategoria.estabelecimentoId(),
                                    t.nomeTamanho(), cadastroCategoria.estabelecimentoId());

                    if (tm == null) {
                        Tamanhos novoTamanho = tamanhosRepository.save(new Tamanhos(t));
                        tamanho.add(novoTamanho);
                    } else {
                        System.out.println("Erro ao salvar tamanho" + t);
                        return ResponseEntity.badRequest()
                                .body("Erro ao Salvar categoria! O tamanho \"" + t.nomeTamanho() + "\" já existe");
                    }
                } else {
                    var tm = tamanhosRepository.getReferenceById(t.id());
                    tamanho.add(tm);
                }
            }
            ;

            List<Massas> massa = new ArrayList<>();
            for (var m : cadastroCategoria.massas()) {
                if (m.id().equals("")) {

                    var bd = categoriaRepository
                            .findByNomeCategoriaAndEstabelecimentoIdAndMassasNomeMassaAndMassasEstabelecimentoId(
                                    cadastroCategoria.nomeCategoria(), cadastroCategoria.estabelecimentoId(),
                                    m.nomeMassa(), cadastroCategoria.estabelecimentoId());
                    if (bd == null) {
                        Massas novaMassa = massasRepository.save(new Massas(m));
                        massa.add(novaMassa);
                    } else {
                        System.out.println("Erro ao salvar categoria" + m);
                        return ResponseEntity.badRequest()
                                .body("Erro ao Salvar categoria! O tipo de massa \"" + m.nomeMassa() + "\" já existe");
                    }
                } else {
                    var ms = massasRepository.getReferenceById(m.id());
                    massa.add(ms);
                }

            }
            ;

            List<Bordas> borda = new ArrayList<>();
            for (var b : cadastroCategoria.bordas()) {

                if (b.id().equals("")) {

                    var bd = categoriaRepository
                            .findByNomeCategoriaAndEstabelecimentoIdAndBordasNomeBordaAndBordasEstabelecimentoId(
                                    cadastroCategoria.nomeCategoria(), cadastroCategoria.estabelecimentoId(),
                                    b.nomeBorda(), cadastroCategoria.estabelecimentoId());
                    if (bd == null) {
                        Bordas novaBorda = bordasRepository.save(new Bordas(b));
                        borda.add(novaBorda);
                    } else {
                        System.out.println("Erro ao salvar categoria" + b);
                        return ResponseEntity.badRequest()
                                .body("Erro ao Salvar categoria! O tipo de borda \"" + b.nomeBorda() + "\" já existe");
                    }

                } else {
                    var bd = bordasRepository.getReferenceById(b.id());
                    borda.add(bd);
                }

            }
            ;

            Categoria categoria = new Categoria(cadastroCategoria);
            categoria.setTamanhos(tamanho);
            categoria.setMassas(massa);
            categoria.setBordas(borda);

            categoriaRepository.save(categoria);

            System.out.println("Categoria Salva com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao Salvar categoria: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("{idestab}")
    @Cacheable(value = CacheNames.NOME_CACHE_CATEGORIA)
    public List<DadosListagemCategorias> listar(@PathVariable String idestab) {
        return categoriaRepository.findAllByEstabelecimentoIdOrderByIndex(idestab).stream()
                .map(DadosListagemCategorias::new)
                .toList();

    }

    @PutMapping("atualizarindex")
    @CacheEvict(value = CacheNames.NOME_CACHE_CATEGORIA, allEntries = true)
    @Transactional
    public ResponseEntity<String> atualizarIndexCategoria(
            @RequestBody @Valid List<DadosAtualizacaoCategoria> categorias) {
        categorias.forEach((c) -> {
            var categoria = categoriaRepository.getReferenceById(c.id());
            categoria.atualizarCategoria(c);
        });

        return ResponseEntity.ok().build();
        
    }

    @DeleteMapping("apagartudo")
    @CacheEvict(value = CacheNames.NOME_CACHE_CATEGORIA, allEntries = true)
    public ResponseEntity<String> apagaCategoria(@RequestBody @Valid DadosApagarCategoria categorias) {
        try {

            categoriaRepository.deleteByNomeCategoriaAndEstabelecimentoId(categorias.nomeCategoria(),
                    categorias.estabelecimentoId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Erro! " + e.getMessage());
        }
    }
}
